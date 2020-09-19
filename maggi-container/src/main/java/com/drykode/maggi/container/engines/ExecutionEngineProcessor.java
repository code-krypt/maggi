package com.drykode.maggi.container.engines;

import com.drykode.maggi.container.task.models.TaskInput;
import com.drykode.maggi.container.task.models.TaskOutput;

public interface ExecutionEngineProcessor {
  TaskOutput execute(String sourceCode, TaskInput taskInput);
}
