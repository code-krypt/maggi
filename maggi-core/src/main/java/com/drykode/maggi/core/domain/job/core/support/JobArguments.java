package com.drykode.maggi.core.domain.job.core.support;

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
public class JobArguments implements Serializable {

  private static final long serialVersionUID = 5691385421626768243L;

  private String sourceConnectionString;

  private String sinkConnectionString;

  private String outputFilePrefix;
}
