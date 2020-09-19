package com.drykode.maggi.io.aws.dynamodb.model;

import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class DynamoDbScanQuery {

  public final String tableName;

  public final QuerySpec querySpec;
}
