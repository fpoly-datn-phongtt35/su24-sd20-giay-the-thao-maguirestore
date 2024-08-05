package com.datn.maguirestore.config;

import com.amazonaws.ApacheHttpClientConfig;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import java.io.IOException;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;

@Component
public class AWSS3Config {

  private static AmazonS3 s3Client;

  @Value("${aws.s3.accessKey}")
  private String accessKey;

  @Value("${aws.s3.secretKey}")
  private String secretKey;

  @Value("${aws.s3.bucketName}")
  private String bucketName;

//  @Bean
//  public AmazonS3 amazonS3() {
//    BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
//    return AmazonS3ClientBuilder.standard()
//        .withRegion(Regions.AP_SOUTHEAST_2)
//        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
//        .build();
//  }

  @Bean
  public AmazonS3 amazonS3() throws Exception {
    BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

    SSLContext sslContext = new SSLContextBuilder()
            .loadTrustMaterial(null, (chain, authType) -> true)
            .build();

    CloseableHttpClient httpClient = HttpClients.custom()
            .setSSLContext(sslContext)
            .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
            .build();

    ClientConfiguration clientConfig = new ClientConfiguration();
    clientConfig.setProtocol(Protocol.HTTPS);

    AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.AP_SOUTHEAST_2)
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
            .withClientConfiguration(clientConfig)
            .withPathStyleAccessEnabled(true)
            .withChunkedEncodingDisabled(true)
            .withPayloadSigningEnabled(false);
//            .withHttpClient(ApacheHttpClient.builder()
//                    .httpClient(httpClient)
//                    .build());

    return builder.build();
  }

  public byte[] downloadImage(String key) {
    S3Object s3Object = s3Client.getObject(bucketName, key);
    S3ObjectInputStream inputStream = s3Object.getObjectContent();
    byte[] byteArray = null;
    try {
      byteArray = IOUtils.toByteArray(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return byteArray;
  }

}
