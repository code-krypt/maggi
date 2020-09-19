package com.drykode.maggi.connector.base.connection.models;

import com.drykode.maggi.connector.base.enums.MaggiConnector;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConnectionParameters {

  private final MaggiConnector connector;

  private final Map<String, String> properties;
}
