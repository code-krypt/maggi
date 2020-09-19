package com.drykode.maggi.client.api.persistance.dynamodb.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.drykode.maggi.client.api.persistance.domains.JobProgress;
import com.drykode.maggi.core.domain.job.progress.support.JobStatus;
import com.drykode.maggi.io.aws.dynamodb.DynamoDbIoClient;
import com.drykode.maggi.io.aws.dynamodb.model.DynamoDbScanQuery;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DynamoDbJobProgressRepository {

  @Autowired private DynamoDbIoClient dynamoDbIoClient;

  @Value("${maggi.job.progress.table:job-progress-table}")
  private String dynamoTable;

  public List<JobProgress> fetch(String apiKey) {

    QuerySpec querySpec = new QuerySpec().withHashKey("api_key", apiKey);

    ItemCollection<QueryOutcome> itemCollection =
        dynamoDbIoClient.read(
            DynamoDbScanQuery.builder().tableName(dynamoTable).querySpec(querySpec).build());

    Iterator<Item> iterator = itemCollection.iterator();
    Item item;

    List<JobProgress> resultList = new ArrayList<>();
    while (iterator.hasNext()) {
      item = iterator.next();

      String jobId = item.getString("job_id");
      JobStatus jobStatus = JobStatus.valueOf(item.getString("status"));

      resultList.add(new JobProgress(jobId, jobStatus));
    }

    return resultList;
  }
}
