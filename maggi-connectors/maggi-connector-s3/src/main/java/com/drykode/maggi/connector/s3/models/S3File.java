package com.drykode.maggi.connector.s3.models;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class S3File {

  private final String fileName;

  private final String fileContent;
}
