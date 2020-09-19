package com.drykode.maggi.connector.base;

import com.drykode.maggi.connector.base.connection.models.ConnectionParameters;
import java.util.List;

public interface Connector<T> {

  List<String> listFiles(String connectionString);

  void writeFile(String connectionString, T file);

  T readFile(String connectionString, String fileName);

  ConnectionParameters parseConnectionString(String connectionString);
}
