package com.drykode.maggi.io.aws.dynamodb.model;

import com.amazonaws.services.dynamodbv2.document.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class DynamoDbItem {

  public final String tableName;

  public final Item tableItem;
}
