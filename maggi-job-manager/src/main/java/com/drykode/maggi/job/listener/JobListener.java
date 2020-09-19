package com.drykode.maggi.job.listener;

import com.drykode.maggi.core.domain.job.core.Job;
import com.drykode.maggi.job.manager.JobManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobListener {

  @Autowired private JobManager jobManager;

  @JmsListener(destination = "${maggi.client.job.submit.queue}")
  public void listen(@Payload final Message<Job> jobMessage) {
    Job job = jobMessage.getPayload();
    log.info(job.toString());
    jobManager.process(job);
  }
}
