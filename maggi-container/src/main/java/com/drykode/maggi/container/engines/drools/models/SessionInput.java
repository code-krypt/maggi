package com.drykode.maggi.container.engines.drools.models;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class SessionInput {
  private final String input;
}
