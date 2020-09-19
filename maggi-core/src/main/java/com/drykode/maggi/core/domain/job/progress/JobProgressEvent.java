package com.drykode.maggi.core.domain.job.progress;

import com.drykode.maggi.core.domain.job.progress.support.JobStatus;
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
public class JobProgressEvent implements Serializable {

  private static final long serialVersionUID = -7458299151557012644L;

  private String jobId;

  private String submitterApiKey;

  private JobStatus status;
}
