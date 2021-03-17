package com.homemade.etl.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.homemade.etl.common.exception.OperationFailedException;
import com.homemade.etl.common.utils.StringHelper;
import com.homemade.etl.service.AwsS3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;


@Slf4j
@Service
public class AwsS3ServiceImpl implements AwsS3Service {

    private final String S3_OBJECT_DELIM = "/";

    private final AmazonS3 s3Client;
    @Autowired
    public AwsS3ServiceImpl(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public boolean exists(String bucketName, String folderName, String fileName) {
        String objectKey = StringHelper.isBlank(folderName)
                ? fileName
                : folderName + S3_OBJECT_DELIM + fileName;
        return exists(bucketName, objectKey);
    }

    @Override
    public boolean exists(String bucketName, String s3ObjectKey) {
        try {
            return s3Client.doesObjectExist(bucketName, s3ObjectKey);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new OperationFailedException(ex.getMessage());
        }
    }

    @Override
    public void createFolder(String bucketName, String folderName) {
        String s3FolderKey = folderName + S3_OBJECT_DELIM;
        createS3FolderByKey(bucketName, s3FolderKey);
    }

    @Override
    public void uploadFile(String bucketName, String folderName, File file) {
        String objectKey = StringHelper.isBlank(folderName) ? file.getName() : folderName + S3_OBJECT_DELIM + file.getName();
        uploadObject(bucketName, objectKey, file);
    }

    @Override
    public void uploadObject(String bucketName, String s3ObjectKey, File file) {
        try {
            PutObjectRequest request = new PutObjectRequest(bucketName, s3ObjectKey, file);

            // upload file
            PutObjectResult response = s3Client.putObject(request);

            log.debug("File {} uploaded. [MD5: {}]", file.getAbsolutePath(), response.getContentMd5());
        } catch (Exception ex) {
            log.error(String.format("Key: %s, Error: %s", s3ObjectKey, ex.getMessage()), ex);
            throw new OperationFailedException(ex.getMessage());
        }
    }

    private void createS3FolderByKey(String s3BucketName, String s3ObjectKey) {
        try {
            // create meta-data for the folder and set content-length to 0
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(0);

            // create empty content
            InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

            // create a PutObjectRequest passing the folder name suffixed by /
            PutObjectRequest putObjectRequest = new PutObjectRequest(s3BucketName, s3ObjectKey, emptyContent, metadata);

            // send request to S3 to create folder
            s3Client.putObject(putObjectRequest);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new OperationFailedException(ex.getMessage());
        }
    }

}
