package com.drykode.maggi.job.manager;

import com.drykode.maggi.connector.base.Connector;
import com.drykode.maggi.connector.s3.models.S3File;
import com.drykode.maggi.core.domain.chefs.Chef;
import com.drykode.maggi.core.domain.job.core.Job;
import com.drykode.maggi.core.domain.task.core.Task;
import com.drykode.maggi.job.task.dispatch.JobPartitioner;
import com.drykode.maggi.job.task.dispatch.TaskDispatcher;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobManager {

  private final Connector<S3File> sourceConnector;

  private final JobPartitioner jobPartitioner;

  private final TaskDispatcher taskDispatcher;

  @Autowired
  public JobManager(
      Connector<S3File> sourceConnector,
      JobPartitioner jobPartitioner,
      TaskDispatcher taskDispatcher) {
    this.sourceConnector = sourceConnector;
    this.jobPartitioner = jobPartitioner;
    this.taskDispatcher = taskDispatcher;
  }

  public void process(Job job) {

    // Get all files names
    String sourceConnectionString = job.getJobArguments().getSourceConnectionString();
    List<String> fileIds = fetchFileList(sourceConnectionString);

    // Split Job into Tasks (FileList is split into Chunks & a Task is allocated to a quorum)
    Map<Task, List<Chef>> taskDistributions = jobPartitioner.partition(job, fileIds);

    // Dispatch each task
    taskDistributions.forEach((task, chefs) -> taskDispatcher.dispatch(task, null));
  }

  private List<String> fetchFileList(String sourceConnectionString) {
    return sourceConnector.listFiles(sourceConnectionString);
  }
}
