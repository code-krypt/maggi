package com.drykode.maggi.client.core.job.dispatcher;

import com.drykode.maggi.core.domain.job.core.Job;

public interface JobDispatcher {

  String dispatch(Job job);
}
