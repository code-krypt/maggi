package com.drykode.maggi.client.api.services;

import com.drykode.maggi.client.api.dtos.JobDto;
import com.drykode.maggi.client.api.dtos.JobProgressDto;
import com.drykode.maggi.client.api.persistance.domains.JobProgress;
import com.drykode.maggi.client.api.persistance.dynamodb.repository.DynamoDbJobProgressRepository;
import com.drykode.maggi.client.core.job.dispatcher.JobDispatcher;
import com.drykode.maggi.core.domain.job.core.Job;
import java.util.List;
import java.util.stream.Collectors;
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

    Job job = toJobDomain(jobDto);
    Job modifiedJob = job.toBuilder().submitterApiKey(submitterApiKey).build();

    String jobId = jobDispatcher.dispatch(modifiedJob);
    return jobDto.toBuilder().id(jobId).build();
  }

  public List<JobProgressDto> fetchJobs() {
    return jobProgressRepository.fetch(submitterApiKey).stream()
        .map(this::toJobProgressDto)
        .collect(Collectors.toList());
  }

  private JobProgressDto toJobProgressDto(JobProgress jobProgress) {
    return JobProgressDto.builder()
        .jobId(jobProgress.getJobId())
        .jobStatus(jobProgress.getJobStatus())
        .build();
  }

  private Job toJobDomain(JobDto dto) {
    return Job.builder()
        .programCode(dto.getProgramCode())
        .jobArguments(dto.getJobArguments())
        .jobExecutionParameters(dto.getJobExecutionParameters())
        .build();
  }
}
