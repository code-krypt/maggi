package com.drykode.maggi.container.task.models;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class TaskOutput {

  // File Id/Name : Input File Content
  private final Map<String, String> outputs;
}
