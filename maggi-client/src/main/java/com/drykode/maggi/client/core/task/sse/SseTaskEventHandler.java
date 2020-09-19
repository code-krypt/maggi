package com.drykode.maggi.client.core.task.sse;

import com.drykode.maggi.client.core.task.TaskEventHandler;
import com.drykode.maggi.container.task.processor.TaskProcessor;
import com.drykode.maggi.core.domain.task.core.Task;
import java.time.LocalTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
@Slf4j
public class SseTaskEventHandler implements TaskEventHandler {

  @Value("${maggi.job.manager.endpoint.task}")
  private String jobManagerTaskEndpoint;

  @Autowired private TaskProcessor taskProcessor;

  @Override
  public void listen() {

    WebClient client = WebClient.create(jobManagerTaskEndpoint);

    ParameterizedTypeReference<ServerSentEvent<Task>> type =
        new ParameterizedTypeReference<ServerSentEvent<Task>>() {};

    Flux<ServerSentEvent<Task>> eventStream =
        client.get().uri("/stream").retrieve().bodyToFlux(type);

    eventStream.subscribe(
        content -> {
          logEvent(content);
          handle(content.data());
        },
        error -> log.error("Error receiving Task: {}", error.getMessage(), error),
        () -> log.info("Task Stream Closed."));
  }

  @Override
  public void handle(Task task) {
    taskProcessor.process(task);
  }

  private void logEvent(ServerSentEvent<Task> content) {
    log.info(
        "Time: {} - event: name[{}], id [{}], content[{}] ",
        LocalTime.now(),
        content.event(),
        content.id(),
        content.data());
  }
}
