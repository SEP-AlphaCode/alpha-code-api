package com.alphacode.alphacodeapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.File;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl {

    private final S3Client s3Client;
    private final Region awsRegion;

    @Value("${application.bucket.name}")
    private String bucketName;

    public String uploadFile(File file) {
        String key = "qrcodes/" + file.getName();

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .contentType("image/png")
                        .build(),
                RequestBody.fromFile(file)
        );

        // Trả về URL public
        return String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucketName,
                awsRegion.id(),
                key
        );
    }

    public String uploadBytes(byte[] data, String key, String contentType) {
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .contentType(contentType)
                        .build(),
                RequestBody.fromBytes(data)
        );

        return String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucketName,
                awsRegion.id(),
                key
        );
    }

}

