package com.drykode.maggi.client.core.job.dispatcher.mq;

import com.drykode.maggi.client.core.job.dispatcher.JobDispatcher;
import com.drykode.maggi.core.domain.job.core.Job;
import com.drykode.maggi.io.jms.producer.MessageProducer;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MessagingJobDispatcher implements JobDispatcher {

  @Autowired private MessageProducer messageProducer;

  @Value("${maggi.client.job.submit.queue}")
  private String jobSubmitQueue;

  public String dispatch(Job job) {
    String jobId = UUID.randomUUID().toString();
    Job updatedJob = job.toBuilder().id(jobId).build();
    messageProducer.send(jobSubmitQueue, updatedJob);
    return jobId;
  }
}
