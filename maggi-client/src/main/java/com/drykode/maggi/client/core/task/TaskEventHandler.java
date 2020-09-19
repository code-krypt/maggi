package com.drykode.maggi.client.core.task;

import com.drykode.maggi.core.domain.task.core.Task;

public interface TaskEventHandler {

  void listen();

  void handle(Task task);
}
