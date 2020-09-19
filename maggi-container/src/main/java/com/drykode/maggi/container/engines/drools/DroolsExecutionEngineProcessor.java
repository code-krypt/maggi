package com.drykode.maggi.container.engines.drools;

import com.drykode.maggi.container.engines.drools.exceptions.DroolsBuildException;
import com.drykode.maggi.container.engines.drools.models.SessionInput;
import com.drykode.maggi.container.engines.drools.models.SessionOutputCollector;
import com.drykode.maggi.container.engines.ExecutionEngineProcessor;
import com.drykode.maggi.container.task.models.TaskInput;
import com.drykode.maggi.container.task.models.TaskOutput;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.Message.Level;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DroolsExecutionEngineProcessor implements ExecutionEngineProcessor {

  public TaskOutput execute(String sourceCode, TaskInput taskInput) {

    KieContainer kContainer = createKieContainer(sourceCode);

    KieSession kieSession = kContainer.newKieSession();

    Map<String, String> executionResults = new HashMap<>();

    taskInput
        .getInputs()
        .forEach(
            (key, value) -> {
              SessionInput si = SessionInput.builder().input(value).build();
              String result = executeSession(si, kieSession);
              executionResults.put(key, result);
            });

    kieSession.dispose();

    return TaskOutput.builder().outputs(executionResults).build();
  }

  private String executeSession(SessionInput sessionInput, KieSession kieSession) {

    SessionOutputCollector sessionOutputCollector = new SessionOutputCollector();
    kieSession.setGlobal("soc", sessionOutputCollector);

    kieSession.insert(sessionInput);

    kieSession.fireAllRules();
    return sessionOutputCollector.getResult();
  }

  private KieContainer createKieContainer(String sourceCode) {

    // Create DRL file
    KieServices kieServices = KieServices.Factory.get();
    KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
    kieFileSystem.write("src/main/resources/com/drykode/maggi/container/code.drl", sourceCode);

    KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
    kieBuilder.buildAll();

    if (kieBuilder.getResults().hasMessages(Level.ERROR)) {
      throw new DroolsBuildException("Build Error :" + kieBuilder.getResults().toString());
    }
    KieModule kieModule = kieBuilder.getKieModule();

    return kieServices.newKieContainer(kieModule.getReleaseId());
  }
}
