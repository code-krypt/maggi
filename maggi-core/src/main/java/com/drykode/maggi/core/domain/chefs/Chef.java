package com.drykode.maggi.core.domain.chefs;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Chef {

  private final String ip;

  private final String deviceId;
}
