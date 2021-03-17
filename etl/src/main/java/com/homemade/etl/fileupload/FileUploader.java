package com.homemade.etl.fileupload;

import com.homemade.etl.service.AwsS3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;


@Slf4j
@Component
public class FileUploader {

    private static final String CONVERTED_FILES_DIR = "converted_files";

    @Value("${amazon.s3.bucket}")
    private String destinationBucketName;

    private final AwsS3Service amazonS3Service;
    @Autowired
    public FileUploader(AwsS3Service amazonS3Service) {
        this.amazonS3Service = amazonS3Service;
    }

    /**
     * Executes initialization logic.
     */
    @PostConstruct
    private void init() {
        // Below code comment outed due to S3 endpoint not availability

//        if (!amazonS3Service.exists(destinationBucketName, String.format("%s/", CONVERTED_FILES_DIR))) {
//            amazonS3Service.createFolder(destinationBucketName, CONVERTED_FILES_DIR);
//        }
    }

    public void upload(File file) {
        amazonS3Service.uploadFile(destinationBucketName, CONVERTED_FILES_DIR, file);
    }

}
