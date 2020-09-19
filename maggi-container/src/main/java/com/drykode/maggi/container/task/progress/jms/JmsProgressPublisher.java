package com.drykode.maggi.container.task.progress.jms;

import com.drykode.maggi.container.task.progress.ProgressPublisher;
import com.drykode.maggi.core.domain.task.progress.TaskProgressEvent;
import com.drykode.maggi.io.jms.producer.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JmsProgressPublisher implements ProgressPublisher {

  @Value("${maggi.client.task.progress.queue}")
  private String progressQueue;

  @Autowired private MessageProducer messageProducer;

  @Override
  public void publish(TaskProgressEvent progressEvent) {
    messageProducer.send(progressQueue, progressEvent);
  }
}
