package com.drykode.maggi.job.task.dispatch;

import com.drykode.maggi.core.domain.chefs.Chef;
import com.drykode.maggi.core.domain.task.core.Task;

public interface TaskDispatcher {

  void dispatch(Task task, Chef chef);
}
