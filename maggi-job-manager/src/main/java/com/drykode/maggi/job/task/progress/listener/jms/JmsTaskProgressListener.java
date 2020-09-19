package com.drykode.maggi.job.task.progress.listener.jms;

import com.drykode.maggi.core.domain.task.progress.TaskProgressEvent;
import com.drykode.maggi.job.task.progress.handler.TaskProgressHandler;
import com.drykode.maggi.job.task.progress.listener.TaskProgressListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JmsTaskProgressListener implements TaskProgressListener<Message<TaskProgressEvent>> {

  @Autowired private TaskProgressHandler taskProgressHandler;

  @Override
  @JmsListener(destination = "${maggi.client.task.progress.queue}")
  public void listen(@Payload final Message<TaskProgressEvent> progressEvent) {
    TaskProgressEvent payload = progressEvent.getPayload();

    log.info(payload.toString());
    taskProgressHandler.handle(payload);
  }
}
