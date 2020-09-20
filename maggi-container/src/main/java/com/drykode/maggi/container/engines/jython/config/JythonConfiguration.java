package com.drykode.maggi.container.engines.jython.config;

import java.util.Properties;
import org.python.util.PythonInterpreter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JythonConfiguration {

  @Bean
  public PythonInterpreter configureInterpreter() {

    final Properties systemProperties = System.getProperties();
    Properties jythonProperties = new Properties();
    jythonProperties.setProperty("python.import.site", "false");

    PythonInterpreter.initialize(systemProperties, jythonProperties, new String[0]);
    return new PythonInterpreter();
  }
}
