package com.drykode.maggi.job.task.dispatch.sse.support;

import com.drykode.maggi.core.domain.task.core.Task;
import org.springframework.stereotype.Component;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.FluxSink;

@Component
public class SseNotification {

  private final FluxProcessor<Task, Task> processor;
  private final FluxSink<Task> sink;

  public SseNotification() {
    this.processor = DirectProcessor.<Task>create().serialize();
    this.sink = processor.sink();
  }

  public void addNotification(Task notification) {
    sink.next(notification);
  }

  public FluxProcessor<Task, Task> getEventStream() {
    return processor;
  }
}
