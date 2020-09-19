package com.drykode.maggi.job.task.dispatch.sse;

import com.drykode.maggi.core.domain.chefs.Chef;
import com.drykode.maggi.core.domain.task.core.Task;
import com.drykode.maggi.job.task.dispatch.TaskDispatcher;
import com.drykode.maggi.job.task.dispatch.sse.support.SseNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SseTaskDispatcher implements TaskDispatcher {

  @Autowired private SseNotification sseNotification;

  @Override
  public void dispatch(Task task, Chef chef) {
    sseNotification.addNotification(task);
  }
}
