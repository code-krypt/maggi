package com.drykode.maggi.client.api.dtos;

import com.drykode.maggi.core.domain.job.progress.support.JobStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class JobProgressDto {

  @JsonProperty private String jobId;

//  @JsonProperty private String jobName;

  @JsonProperty private JobStatus jobStatus;
}
