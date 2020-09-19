package com.drykode.maggi.client.core.job.progress;

import com.drykode.maggi.core.domain.job.progress.JobProgressEvent;

public interface ProgressListener {

  void listen(JobProgressEvent jobProgressEvent);
}
