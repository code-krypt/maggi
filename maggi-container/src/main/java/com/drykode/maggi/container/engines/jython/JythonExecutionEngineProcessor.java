package com.drykode.maggi.container.engines.jython;

import com.drykode.maggi.container.engines.ExecutionEngineProcessor;
import com.drykode.maggi.container.task.models.TaskInput;
import com.drykode.maggi.container.task.models.TaskOutput;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Primary
@Component
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class JythonExecutionEngineProcessor implements ExecutionEngineProcessor {

  @Autowired private PythonInterpreter interpreter;

  @Override
  public TaskOutput execute(String sourceCode, TaskInput taskInput) {

    Map<String, String> input = taskInput.getInputs();
    Map<String, String> result = new LinkedHashMap<>();

    input.forEach(
        (fileName, fileContent) -> {
          interpreter.set("input", new PyString(fileContent));
          interpreter.exec(sourceCode);
          PyObject output = interpreter.get("output");
          result.put(fileName, output.toString());
        });

    return TaskOutput.builder().outputs(result).build();
  }
}
