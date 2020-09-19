package com.drykode.maggi.io.jms.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ErrorHandler;

@Slf4j
public class JmsErrorHandler implements ErrorHandler {

  @Override
  public void handleError(Throwable throwable) {
    log.error("Jms Error", throwable);
  }
}
