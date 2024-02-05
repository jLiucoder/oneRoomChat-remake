package com.nyu.IntrotoJava.finalProject.OneRoomChatApp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.time.Duration;

@Slf4j
@Service
public class StorageService {

    public String generatePresignedUrl(String bucketName, String objectKey, String contentType) {
        S3Presigner presigner = S3Presigner.builder()
                .region(Region.US_EAST_2) // Specify your region
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .contentType(contentType)
                .build();

        PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(z -> z.signatureDuration(Duration.ofMinutes(10))
                .putObjectRequest(objectRequest));

        presigner.close();
        log.info("Presigned URL to upload a file: {}", presignedRequest.url());
        return presignedRequest.url().toString();
    }
}
