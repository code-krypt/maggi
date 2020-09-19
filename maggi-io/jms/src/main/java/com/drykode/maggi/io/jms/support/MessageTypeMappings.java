package com.drykode.maggi.io.jms.support;

import java.util.Map;

@FunctionalInterface
public interface MessageTypeMappings {
  Map<String, Class<?>> get();
}
