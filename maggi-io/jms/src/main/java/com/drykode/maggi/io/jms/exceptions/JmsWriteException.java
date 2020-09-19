package com.drykode.maggi.io.jms.exceptions;

public class JmsWriteException extends RuntimeException {

  private static final String MESSAGE = "JMS Write Failed.";

  public JmsWriteException() {
    super(MESSAGE);
  }

  public JmsWriteException(Throwable ex) {
    super(MESSAGE, ex);
  }
}
