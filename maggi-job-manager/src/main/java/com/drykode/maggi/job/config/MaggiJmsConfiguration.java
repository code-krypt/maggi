package com.drykode.maggi.job.config;

import com.drykode.maggi.core.domain.job.core.Job;
import com.drykode.maggi.io.jms.support.MessageTypeMappings;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MaggiJmsConfiguration {

  @Bean
  public MessageTypeMappings messageTypeIdMappings() {
    Map<String, Class<?>> mappings = new HashMap<>();
    mappings.put(Job.class.getSimpleName(), Job.class);
    return () -> mappings;
  }
}
