package com.drykode.maggi.container.task.progress;

import com.drykode.maggi.core.domain.task.progress.TaskProgressEvent;

public interface ProgressPublisher {
  void publish(TaskProgressEvent progressEvent);
}
