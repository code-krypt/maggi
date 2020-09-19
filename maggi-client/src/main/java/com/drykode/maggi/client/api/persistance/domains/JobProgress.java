package com.drykode.maggi.client.api.persistance.domains;

import com.drykode.maggi.core.domain.job.progress.support.JobStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JobProgress {
  private String jobId;

  //  private String jobName;

  private JobStatus jobStatus;
}
