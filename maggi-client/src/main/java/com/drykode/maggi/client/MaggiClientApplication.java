package com.drykode.maggi.client;

import com.drykode.maggi.client.core.task.TaskEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(
    basePackages = {
      "com.drykode.maggi.client",
      "com.drykode.maggi.maggi.core",
      "com.drykode.maggi.io.jms",
      "com.drykode.maggi.container",
      "com.drykode.maggi.connector.s3",
      "com.drykode.maggi.io.aws.s3",
      "com.drykode.maggi.io.aws.dynamodb"
    })
@Slf4j
public class MaggiClientApplication implements CommandLineRunner {

  @Autowired private TaskEventHandler taskEventHandler;

  @Value("${maggi.api.key:default-client-id}")
  private String apiKey;

  public static void main(String[] args) {
    SpringApplication.run(MaggiClientApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    log.info("Maggi initialized for {}", apiKey);
    taskEventHandler.listen();
  }
}
