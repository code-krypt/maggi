package com.drykode.maggi.connector.base.connection.utils;

import static com.drykode.maggi.connector.base.constants.ConnectorConstants.EQUALS_CONSTANT;
import static com.drykode.maggi.connector.base.constants.ConnectorConstants.PROPERTIES_DELIMITER;
import static java.util.stream.Collectors.toMap;

import com.drykode.maggi.connector.base.connection.models.ConnectionParameters;
import com.drykode.maggi.connector.base.enums.MaggiConnector;
import java.util.Arrays;
import java.util.Map;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConnectionUtils {

  public ConnectionParameters parse(String connectionString) {

    String[] propertiesArray = connectionString.split(PROPERTIES_DELIMITER);

    String connectorName = propertiesArray[0].split(EQUALS_CONSTANT)[1];
    MaggiConnector connector = MaggiConnector.valueOf(connectorName);

    Map<String, String> propertyMap =
        Arrays.stream(propertiesArray)
            .skip(1)
            .map(e -> e.split(EQUALS_CONSTANT))
            .collect(toMap(k -> k[0], v -> v[1]));

    return ConnectionParameters.builder().connector(connector).properties(propertyMap).build();
  }
}
