package com.homemade.etl.service;

import java.io.File;


public interface AwsS3Service {

    /**
     * Checks if an object (file) with specified name exists in selected root directory
     * of the specified S3 bucket.
     */
    boolean exists(String bucketName, String folderName, String fileName);

    /**
     * Checks if an S3 object exists with specified KEY.
     */
    boolean exists(String bucketName, String s3ObjectKey);

    /**
     * Creates a root directory in specified S3 bucket.
     */
    void createFolder(String bucketName, String folderName);

    /**
     * Uploads selected file to specified root directory of the S3 bucket.
     */
    void uploadFile(String bucketName, String folderName, File file);

    /**
     * Uploads a file using the S3 KEY.
     */
    void uploadObject(String bucketName, String s3ObjectKey, File file);

}
