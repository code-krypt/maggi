package com.drykode.maggi.client.api.services;

import com.drykode.maggi.client.api.dtos.JobDto;
import com.drykode.maggi.client.api.persistance.dynamodb.DynamoDbJobProgressRepository;
import com.drykode.maggi.client.core.job.dispatcher.JobDispatcher;
import com.drykode.maggi.core.domain.job.core.Job;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JobService {

  @Value("${maggi.api.key:default-client-id}")
  private String submitterApiKey;

  @Autowired private JobDispatcher jobDispatcher;

  @Autowired private DynamoDbJobProgressRepository jobProgressRepository;

  public JobDto submitJob(JobDto jobDto) {

    Job job = toDomain(jobDto);
    Job modifiedJob = job.toBuilder().submitterApiKey(submitterApiKey).build();

    String jobId = jobDispatcher.dispatch(modifiedJob);
    return jobDto.toBuilder().id(jobId).build();
  }

  public Map<String, String> fetchJobs() {
    return jobProgressRepository.fetch(submitterApiKey);
  }

  private Job toDomain(JobDto dto) {
    return Job.builder()
        .programCode(dto.getProgramCode())
        .jobArguments(dto.getJobArguments())
        .jobExecutionParameters(dto.getJobExecutionParameters())
        .build();
  }
}
