package com.drykode.maggi.core.domain.aws;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AwsConnectionParameters {

  private final String accessKey;

  private final String secretKey;

  private final String region;
}
