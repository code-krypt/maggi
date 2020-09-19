package com.drykode.maggi.job.task.progress.listener;

public interface TaskProgressListener<T> {

  void listen(T progressEvent);
}
