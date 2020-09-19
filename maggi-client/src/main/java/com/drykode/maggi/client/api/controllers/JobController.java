package com.drykode.maggi.client.api.controllers;

import com.drykode.maggi.client.api.dtos.JobDto;
import com.drykode.maggi.client.api.exceptions.RequestIdPresentException;
import com.drykode.maggi.client.api.services.JobService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobs")
public class JobController {

  @Autowired private JobService jobService;

  @PostMapping("/submit")
  public ResponseEntity<JobDto> submitJob(@RequestBody JobDto request) {

    if (request.isIdSet()) {
      throw new RequestIdPresentException();
    }

    JobDto result = jobService.submitJob(request);
    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

  @GetMapping("/")
  public ResponseEntity<Map<String, String>> fetchJobs() {
    Map<String, String> result = jobService.fetchJobs();
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
