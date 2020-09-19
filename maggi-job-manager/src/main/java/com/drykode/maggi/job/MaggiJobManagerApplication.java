package com.drykode.maggi.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(
    basePackages = {
      "com.drykode.maggi.core",
      "com.drykode.maggi.io.jms",
      "com.drykode.maggi.connector.base",
      "com.drykode.maggi.connector.s3",
      "com.drykode.maggi.job",
      "com.drykode.maggi.io.aws.s3",
      "com.drykode.maggi.io.aws.dynamodb"
    })
public class MaggiJobManagerApplication {

  public static void main(String[] args) {
    SpringApplication.run(MaggiJobManagerApplication.class, args);
  }
}
