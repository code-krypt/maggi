package com.drykode.maggi.io.aws.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.drykode.maggi.core.domain.aws.AwsConnectionParameters;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class S3IoClient {

  @SneakyThrows
  public String readString(
      AwsConnectionParameters awsParameters, String bucketName, String fileKey) {

    AmazonS3 s3Client = connectToS3(awsParameters);

    InputStream inputStream =
        s3Client.getObject(new GetObjectRequest(bucketName, fileKey)).getObjectContent();
    String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    inputStream.close();
    return result;
  }

  public void writeString(
      AwsConnectionParameters awsParameters, String bucketName, String fileName, String content) {

    AmazonS3 s3Client = connectToS3(awsParameters);

    s3Client.putObject(bucketName, fileName, content);
  }

  public List<String> getFileList(AwsConnectionParameters awsParameters, String bucketName) {

    AmazonS3 s3Client = connectToS3(awsParameters);

    ListObjectsV2Result listObjectsResponse = s3Client.listObjectsV2(bucketName);
    List<S3ObjectSummary> objects = listObjectsResponse.getObjectSummaries();

    List<String> s3Files = new ArrayList<>();
    for (S3ObjectSummary s3ObjectSummary : objects) {
      s3Files.add(s3ObjectSummary.getKey());
    }

    return s3Files;
  }

  private AmazonS3 connectToS3(AwsConnectionParameters awsParameters) {

    BasicAWSCredentials basicAWSCredentials =
        new BasicAWSCredentials(awsParameters.getAccessKey(), awsParameters.getSecretKey());
    return AmazonS3ClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
        .withRegion(awsParameters.getRegion())
        .build();
  }
}
