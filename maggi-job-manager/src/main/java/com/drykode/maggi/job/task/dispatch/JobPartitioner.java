package com.drykode.maggi.job.task.dispatch;

import com.drykode.maggi.core.domain.chefs.Chef;
import com.drykode.maggi.core.domain.job.core.Job;
import com.drykode.maggi.core.domain.task.core.Task;
import java.util.List;
import java.util.Map;

public interface JobPartitioner {

  Map<Task, List<Chef>> partition(Job job, List<String> fileIds);
}
