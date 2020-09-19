package com.drykode.maggi.job.task.dispatch.sse.support;

import com.drykode.maggi.core.domain.task.core.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/tasks")
public class SseTaskController {

  @Autowired private SseNotification sseNotification;

  @GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<ServerSentEvent<Task>> streamEvents() {
    return sseNotification
        .getEventStream()
        .map(sequence -> ServerSentEvent.builder(sequence).build());
  }
}
