package com.drykode.maggi.io.aws.dynamodb;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.drykode.maggi.core.domain.aws.AwsConnectionParameters;
import com.drykode.maggi.io.aws.dynamodb.model.DynamoDbItem;
import com.drykode.maggi.io.aws.dynamodb.model.DynamoDbScanQuery;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

// TODO: Create more abstraction on these API
// TODO: Adapt it with connectors
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DynamoDbIoClient {

  public void save(AwsConnectionParameters awsParameters, DynamoDbItem dynamoDbItem) {

    DynamoDB dynamoDbClient = connectToDbWithProvidedCred(awsParameters);

    String tableName = dynamoDbItem.getTableName();
    Table table = dynamoDbClient.getTable(tableName);

    table.putItem(dynamoDbItem.getTableItem());
  }

  public void save(DynamoDbItem dynamoDbItem) {

    DynamoDB dynamoDbClient = connectToDbWithDefaultCred();

    String tableName = dynamoDbItem.getTableName();
    Table table = dynamoDbClient.getTable(tableName);

    table.putItem(dynamoDbItem.getTableItem());
  }

  public ItemCollection<QueryOutcome> read(
      AwsConnectionParameters awsParameters, DynamoDbScanQuery dbScanQuery) {

    DynamoDB dynamoDbClient = connectToDbWithProvidedCred(awsParameters);

    String tableName = dbScanQuery.getTableName();
    Table table = dynamoDbClient.getTable(tableName);

    return table.query(dbScanQuery.getQuerySpec());
  }

  public ItemCollection<QueryOutcome> read(DynamoDbScanQuery dbScanQuery) {

    DynamoDB dynamoDbClient = connectToDbWithDefaultCred();

    String tableName = dbScanQuery.getTableName();
    Table table = dynamoDbClient.getTable(tableName);

    return table.query(dbScanQuery.getQuerySpec());
  }

  private DynamoDB connectToDbWithProvidedCred(AwsConnectionParameters awsParameters) {

    BasicAWSCredentials basicAWSCredentials =
        new BasicAWSCredentials(awsParameters.getAccessKey(), awsParameters.getSecretKey());

    return new DynamoDB(
        AmazonDynamoDBClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
            .withRegion(awsParameters.getRegion())
            .build());
  }

  private DynamoDB connectToDbWithDefaultCred() {

    return new DynamoDB(
        AmazonDynamoDBClientBuilder.standard()
            .withCredentials(new DefaultAWSCredentialsProviderChain())
            .build());
  }
}
