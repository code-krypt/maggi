package com.drykode.maggi.container.task.processor;

import com.drykode.maggi.connector.base.Connector;
import com.drykode.maggi.connector.s3.models.S3File;
import com.drykode.maggi.container.engines.ExecutionEngineProcessor;
import com.drykode.maggi.container.task.models.TaskInput;
import com.drykode.maggi.container.task.models.TaskOutput;
import com.drykode.maggi.container.task.progress.ProgressPublisher;
import com.drykode.maggi.core.domain.task.core.Task;
import com.drykode.maggi.core.domain.task.progress.TaskProgressEvent;
import com.drykode.maggi.core.domain.task.progress.support.TaskStatus;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TaskProcessor {

  @Autowired private Connector<S3File> sourceConnector;
  @Autowired private ExecutionEngineProcessor taskExecutor;
  @Autowired private Connector<S3File> sinkConnector;
  @Autowired private ProgressPublisher progressPublisher;

  // TODO: CoR
  @Async
  public void process(Task task) {
    TaskInput taskInput = extractSourceFiles(task); // extract
    TaskOutput taskOutput = executeTask(taskInput, task); // transform
    loadToSink(taskOutput, task); // load
    publishProgress(task); // publish updates to job-manager
  }

  private TaskInput extractSourceFiles(Task task) {

    String sourceConnectionString = task.getTaskArguments().getSourceConnectionString();

    List<String> fileNames = task.getTaskArguments().getFileIds();

    Map<String, String> files =
        fileNames.stream()
            .map(
                fileName -> {
                  String fileContent =
                      sourceConnector.readFile(sourceConnectionString, fileName).getFileContent();
                  return Arrays.asList(fileName, fileContent);
                })
            .collect(Collectors.toMap(key -> key.get(0), value -> value.get(1)));

    log.info("Task input files " + files.toString());

    return TaskInput.builder().inputs(files).build();
  }

  private TaskOutput executeTask(TaskInput taskInput, Task task) {
    String programCode = task.getProgramCode();
    log.info("Execute Task {} + {}", programCode, taskInput.toString());
    return taskExecutor.execute(programCode, taskInput);
  }

  private void loadToSink(TaskOutput taskOutput, Task task) {

    log.info("Load to sink {}", taskOutput.toString());

    Map<String, String> results = taskOutput.getOutputs();

    results.forEach(
        (fileName, fileContent) -> {
          String sinkConnectionString = task.getTaskArguments().getSinkConnectionString();
          String filePrefix = task.getTaskArguments().getOutputFilePrefix();
          String outputFileName = filePrefix.concat(fileName);
          S3File s3File =
              S3File.builder().fileName(outputFileName).fileContent(fileContent).build();
          sinkConnector.writeFile(sinkConnectionString, s3File);
        });
  }

  private void publishProgress(Task task) {
    log.info("Task progress published {}", task.toString());

    progressPublisher.publish(
        TaskProgressEvent.builder()
            .taskId(task.getId())
            .jobId(task.getJobId())
            .submitterApiKey(task.getSubmitterApiKey())
            .status(TaskStatus.SUCCESS)
            .build());
  }
}
