package com.homemade.etl.fileupload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.BlockingQueue;


@Slf4j
@Component
@Scope("prototype")
public class EtlFileUploader implements Runnable {
    private static final String FILE_DIR = "./convertedfiles";

    private BlockingQueue<String> fileNameQueue;

    private FileUploader fileUploader;
    @Autowired
    public EtlFileUploader(FileUploader fileUploader) {
        this.fileUploader = fileUploader;
    }

    public EtlFileUploader setInputData(BlockingQueue<String> fileNameQueue) {
        this.fileNameQueue = fileNameQueue;
        return this;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String fileName = fileNameQueue.take();

                fileUploader.upload(new File(FILE_DIR + fileName));

                log.info(">>> Converted file with key [{}] was successfully stored", fileName);

            } catch (Exception ex) {
                log.error("Error occurred while uploading converted file [{}]", ex.getMessage());
                // TODO: have retry logic in case of fail
            }
        }
    }
}
