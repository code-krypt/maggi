package com.drykode.maggi.job.task.progress.handler.dynamodb;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.drykode.maggi.core.domain.task.progress.TaskProgressEvent;
import com.drykode.maggi.io.aws.dynamodb.DynamoDbIoClient;
import com.drykode.maggi.io.aws.dynamodb.model.DynamoDbItem;
import com.drykode.maggi.job.task.progress.handler.TaskProgressHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DynamoDbTaskProgressHandler implements TaskProgressHandler {

  @Autowired private DynamoDbIoClient dynamoDbIoClient;

  @Value("${maggi.job.progress.table:job-progress-table}")
  private String dynamoTable;

  @Override
  public void handle(TaskProgressEvent progressEvent) {
    Item item = new Item();
    item.withPrimaryKey(
        "api_key", progressEvent.getSubmitterApiKey(), "job_id", progressEvent.getJobId());
    item.withString("status", progressEvent.getStatus().toString());

    dynamoDbIoClient.save(DynamoDbItem.builder().tableName(dynamoTable).tableItem(item).build());
  }
}
