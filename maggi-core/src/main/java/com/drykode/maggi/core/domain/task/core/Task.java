package com.drykode.maggi.core.domain.task.core;

import com.drykode.maggi.core.domain.task.core.support.TaskArguments;
import com.drykode.maggi.core.domain.task.core.support.TaskExecutionParameters;
import java.io.Serializable;
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
public class Task implements Serializable {

  private static final long serialVersionUID = -5725868942285469056L;

  private String id;

  private String jobId;

  private String submitterApiKey;

  private String programCode;

  private TaskArguments taskArguments;

  private TaskExecutionParameters taskExecutionParameters;
}
