package com.datn.maguirestore.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
@Component
public class AWSS3Util {

    private static final String bucketName = "fpl-datn2024";

    private static AmazonS3 s3Client;

    @Value("${aws.s3.accessKey}")
    private String accessKey;

    @Value("${aws.s3.secretKey}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3Client =
                AmazonS3ClientBuilder
                        .standard()
                        .withCredentials(new AWSStaticCredentialsProvider(credentials))
                        .withRegion(Regions.AP_SOUTHEAST_2)
                        .build();
    }

    public void uploadPhoto(String key, File file) {
        System.out.println("Uploading to bucket: " + bucketName);
        this.s3Client.putObject(bucketName, key, file);
    }

    private static boolean checkForDuplicates(String bucketName) {
        ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request().withBucketName(bucketName);
        ListObjectsV2Result listObjectsResponse = s3Client.listObjectsV2(listObjectsRequest);
        Set<String> seenKeys = new HashSet<>();
        for (S3ObjectSummary objectSummary : listObjectsResponse.getObjectSummaries()) {
            String objectKey = objectSummary.getKey();
            if (seenKeys.contains(objectKey)) {
                return true; // Duplicates found
            } else {
                seenKeys.add(objectKey);
            }
        }
        return false; // No duplicates found
    }

    public byte[] downloadPhoto(String key) {
        S3Object s3object = this.s3Client.getObject(bucketName, key);
        S3ObjectInputStream inputStream = s3object.getObjectContent();
        byte[] byteArray = null;
        try {
            byteArray = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArray;
    }

    public void deleteFile(String key) {
        this.s3Client.deleteObject(bucketName, key);
    }

}
