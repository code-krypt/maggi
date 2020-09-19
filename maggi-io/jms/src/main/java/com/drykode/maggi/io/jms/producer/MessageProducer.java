package com.drykode.maggi.io.jms.producer;

import static com.drykode.maggi.io.jms.constants.MessagingConstants.MESSAGE_ATTRIBUTE_TYPE;

import com.drykode.maggi.io.jms.exceptions.JmsWriteException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageProducer {

  @Resource protected JmsTemplate jmsTemplate;

  @Resource ObjectMapper objectMapper;

  public <I extends Serializable> void send(String queue, I payload) {
    jmsTemplate.send(queue, session -> createMessage(payload, session));
    log.info("Message pushed to MQ.");
  }

  private <I extends Serializable> Message createMessage(I payload, Session session) {
    try {
      Message message = session.createTextMessage(objectMapper.writeValueAsString(payload));
      message.setStringProperty(MESSAGE_ATTRIBUTE_TYPE, payload.getClass().getName());
      return message;
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new JmsWriteException(ex);
    }
  }
}
