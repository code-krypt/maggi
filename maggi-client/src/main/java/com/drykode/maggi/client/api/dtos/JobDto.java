package com.drykode.maggi.client.api.dtos;

import com.drykode.maggi.core.domain.job.core.support.JobArguments;
import com.drykode.maggi.core.domain.job.core.support.JobExecutionParameters;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class JobDto {

  @JsonProperty("id")
  private String id;

  @JsonProperty("programCode")
  private String programCode;

  @JsonProperty("jobArguments")
  private JobArguments jobArguments;

  @JsonProperty("jobExecutionParameters")
  private JobExecutionParameters jobExecutionParameters;

  @JsonIgnore
  public boolean isIdSet() {
    return id != null && !id.isEmpty();
  }
}
