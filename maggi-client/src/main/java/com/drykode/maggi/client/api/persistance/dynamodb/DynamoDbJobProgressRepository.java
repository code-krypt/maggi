package com.drykode.maggi.client.api.persistance.dynamodb;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.drykode.maggi.io.aws.dynamodb.DynamoDbIoClient;
import com.drykode.maggi.io.aws.dynamodb.model.DynamoDbScanQuery;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DynamoDbJobProgressRepository {

  @Autowired private DynamoDbIoClient dynamoDbIoClient;

  @Value("${maggi.job.progress.table:job-progress-table}")
  private String dynamoTable;

  public Map<String, String> fetch(String apiKey) {

    QuerySpec querySpec = new QuerySpec().withHashKey("api_key", apiKey);

    ItemCollection<QueryOutcome> resultList =
        dynamoDbIoClient.read(
            DynamoDbScanQuery.builder().tableName(dynamoTable).querySpec(querySpec).build());

    Iterator<Item> iterator = resultList.iterator();
    Item item;

    Map<String, String> resultMap = new HashMap<>();
    while (iterator.hasNext()) {
      item = iterator.next();
      resultMap.put(item.getString("job_id"), item.getString("status"));
    }

    return resultMap;
  }
}
