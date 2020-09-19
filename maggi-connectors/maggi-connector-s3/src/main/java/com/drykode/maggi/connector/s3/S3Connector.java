package com.drykode.maggi.connector.s3;

import static com.drykode.maggi.connector.base.constants.AwsConstants.AWS_ACCESS_KEY;
import static com.drykode.maggi.connector.base.constants.AwsConstants.AWS_REGION;
import static com.drykode.maggi.connector.base.constants.AwsConstants.AWS_S3_BUCKET_NAME;
import static com.drykode.maggi.connector.base.constants.AwsConstants.AWS_SECRETE_KEY;

import com.drykode.maggi.connector.base.Connector;
import com.drykode.maggi.connector.base.connection.models.ConnectionParameters;
import com.drykode.maggi.connector.base.connection.utils.ConnectionUtils;
import com.drykode.maggi.connector.s3.models.S3File;
import com.drykode.maggi.core.domain.aws.AwsConnectionParameters;
import com.drykode.maggi.io.aws.s3.S3IoClient;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class S3Connector implements Connector<S3File> {

  private final S3IoClient s3IoClient;

  @Autowired
  public S3Connector(S3IoClient s3IoClient) {
    this.s3IoClient = s3IoClient;
  }

  @Override
  public List<String> listFiles(String connectionString) {
    ConnectionParameters connectionParameters = parseConnectionString(connectionString);
    AwsConnectionParameters awsConnectionParameters =
        getAwsConnectionParameters(connectionParameters);
    String bucketName = connectionParameters.getProperties().get(AWS_S3_BUCKET_NAME);
    return s3IoClient.getFileList(awsConnectionParameters, bucketName);
  }

  @Override
  public void writeFile(String connectionString, S3File file) {
    ConnectionParameters connectionParameters = parseConnectionString(connectionString);
    AwsConnectionParameters awsConnectionParameters =
        getAwsConnectionParameters(connectionParameters);
    String bucketName = connectionParameters.getProperties().get(AWS_S3_BUCKET_NAME);
    String fileName = file.getFileName();
    String fileContent = file.getFileContent();
    s3IoClient.writeString(awsConnectionParameters, bucketName, fileName, fileContent);
  }

  @Override
  public S3File readFile(String connectionString, String fileName) {
    ConnectionParameters connectionParameters = parseConnectionString(connectionString);
    AwsConnectionParameters awsConnectionParameters =
        getAwsConnectionParameters(connectionParameters);
    String bucketName = connectionParameters.getProperties().get(AWS_S3_BUCKET_NAME);
    String fileContent = s3IoClient.readString(awsConnectionParameters, bucketName, fileName);
    return S3File.builder().fileName(fileName).fileContent(fileContent).build();
  }

  @Override
  public ConnectionParameters parseConnectionString(String connectionString) {
    return ConnectionUtils.parse(connectionString);
  }

  private AwsConnectionParameters getAwsConnectionParameters(
      ConnectionParameters connectionParameters) {

    return AwsConnectionParameters.builder()
        .accessKey(connectionParameters.getProperties().get(AWS_ACCESS_KEY))
        .secretKey(connectionParameters.getProperties().get(AWS_SECRETE_KEY))
        .region(connectionParameters.getProperties().get(AWS_REGION))
        .build();
  }
}
