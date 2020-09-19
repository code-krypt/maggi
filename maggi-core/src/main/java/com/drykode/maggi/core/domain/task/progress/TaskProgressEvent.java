package com.drykode.maggi.core.domain.task.progress;

import com.drykode.maggi.core.domain.task.progress.support.TaskStatus;
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
public class TaskProgressEvent implements Serializable {

  private static final long serialVersionUID = 8139783172347738331L;

  private String taskId;

  private String jobId;

  private String submitterApiKey;

  private TaskStatus status;
}
