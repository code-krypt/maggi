package com.drykode.maggi.core.domain.job.core;

import com.drykode.maggi.core.domain.job.core.support.JobArguments;
import com.drykode.maggi.core.domain.job.core.support.JobExecutionParameters;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.With;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Job implements Serializable {

  private static final long serialVersionUID = 6949274924128929971L;

  private String id;

  private String submitterApiKey;

  private String programCode;

  private JobArguments jobArguments;

  private JobExecutionParameters jobExecutionParameters;
}
