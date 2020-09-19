package com.drykode.maggi.io.jms.aws.sqs;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import javax.jms.ConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SqsConnectionConfiguration {

  @Bean
  @ConditionalOnMissingBean(ConnectionFactory.class)
  public SQSConnectionFactory connectionFactory() {
    return new SQSConnectionFactory(
        new ProviderConfiguration(), AmazonSQSClientBuilder.defaultClient());
  }
}
