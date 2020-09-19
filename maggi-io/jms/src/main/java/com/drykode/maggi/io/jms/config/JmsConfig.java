package com.drykode.maggi.io.jms.config;

import static com.drykode.maggi.io.jms.constants.MessagingConstants.MESSAGE_ATTRIBUTE_TYPE;

import com.drykode.maggi.io.jms.handlers.JmsErrorHandler;
import com.drykode.maggi.io.jms.support.MessageTypeMappings;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.jms.ConnectionFactory;
import javax.jms.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.util.ErrorHandler;

@Configuration
@EnableJms
@Slf4j
public class JmsConfig {

  @Autowired private MessageTypeMappings messageTypeMappings;

  @Value("${maggi.io.jms.consumer.concurrency.level:1}")
  private String consumerConcurrency;

  @Autowired private ObjectMapper objectMapper;

  @Bean
  public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
    JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
    jmsTemplate.setMessageConverter(messageConverter());
    return jmsTemplate;
  }

  @Bean
  public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
      ConnectionFactory connectionFactory,
      MessageConverter messageConverter,
      ErrorHandler errorHandler) {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setDestinationResolver(new DynamicDestinationResolver());
    factory.setConcurrency(consumerConcurrency);
    factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
    factory.setMessageConverter(messageConverter);
    factory.setErrorHandler(errorHandler);
    return factory;
  }

  @Bean
  public MessageConverter messageConverter() {
    MappingJackson2MessageConverter mappingJackson2MessageConverter =
        new MappingJackson2MessageConverter();

    mappingJackson2MessageConverter.setObjectMapper(objectMapper);

    mappingJackson2MessageConverter.setTargetType(MessageType.TEXT);
    mappingJackson2MessageConverter.setTypeIdPropertyName(MESSAGE_ATTRIBUTE_TYPE);
    mappingJackson2MessageConverter.setTypeIdMappings(messageTypeMappings.get());

    return mappingJackson2MessageConverter;
  }

  @Bean
  @ConditionalOnMissingBean(ErrorHandler.class)
  public ErrorHandler errorHandler() {
    return new JmsErrorHandler();
  }
}
