package com.drykode.maggi.core.domain.task.core.support;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaskArguments implements Serializable {

  private static final long serialVersionUID = -2801841936706413759L;

  private String sourceConnectionString;

  private String sinkConnectionString;

  private List<String> fileIds;

  private String outputFilePrefix;
}
