package com.drykode.maggi.job.task.progress.handler;

import com.drykode.maggi.core.domain.task.progress.TaskProgressEvent;

public interface TaskProgressHandler {

  void handle(TaskProgressEvent progressEvent);
}
