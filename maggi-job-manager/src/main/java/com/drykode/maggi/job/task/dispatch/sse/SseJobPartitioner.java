package com.drykode.maggi.job.task.dispatch.sse;

import com.drykode.maggi.core.domain.chefs.Chef;
import com.drykode.maggi.core.domain.job.core.Job;
import com.drykode.maggi.core.domain.task.core.Task;
import com.drykode.maggi.core.domain.task.core.support.TaskArguments;
import com.drykode.maggi.core.domain.task.core.support.TaskExecutionParameters;
import com.drykode.maggi.job.task.dispatch.JobPartitioner;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class SseJobPartitioner implements JobPartitioner {

  // TODO: Handle routing for chefs.
  public Map<Task, List<Chef>> partition(Job job, List<String> fileIds) {

    Map<Task, List<Chef>> distribution = new HashMap<>();

    String taskId = generateId();

    distribution.put(
        Task.builder()
            .id(taskId)
            .submitterApiKey(job.getSubmitterApiKey())
            .jobId(job.getId())
            .programCode(job.getProgramCode())
            .taskArguments(
                TaskArguments.builder()
                    .fileIds(fileIds)
                    .outputFilePrefix(job.getJobArguments().getOutputFilePrefix())
                    .sourceConnectionString(job.getJobArguments().getSourceConnectionString())
                    .sinkConnectionString(job.getJobArguments().getSinkConnectionString())
                    .build())
            .taskExecutionParameters(
                TaskExecutionParameters.builder()
                    .accessPermissions(job.getJobExecutionParameters().getAccessPermissions())
                    .build())
            .build(),
        Collections.emptyList());

    return distribution;
  }

  public String generateId() {
    return UUID.randomUUID().toString();
  }
}
