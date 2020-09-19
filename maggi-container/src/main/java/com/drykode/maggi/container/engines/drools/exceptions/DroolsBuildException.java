package com.drykode.maggi.container.engines.drools.exceptions;

public class DroolsBuildException extends RuntimeException {

  private static final String MESSAGE = "Build Error :";

  public DroolsBuildException(String message) {
    super(MESSAGE.concat(message));
  }
}
