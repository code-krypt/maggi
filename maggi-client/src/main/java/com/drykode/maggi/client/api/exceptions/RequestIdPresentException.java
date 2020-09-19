package com.drykode.maggi.client.api.exceptions;

public class RequestIdPresentException extends RuntimeException {

  private static final String MESSAGE = "Id is not required in the request.";

  public RequestIdPresentException() {
    super(MESSAGE);
  }
}
