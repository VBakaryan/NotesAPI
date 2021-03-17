package com.homemade.etl.executor;

import com.homemade.etl.converter.JsonConverter;
import com.homemade.etl.converter.ParquetConverter;
import com.homemade.etl.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;


@Slf4j
@Component
@SuppressWarnings("Duplicates")
@Scope("prototype")
public class EtlUserExecutor implements Runnable {

    private String fileName;
    private BlockingQueue<Map<Integer, List<User>>> queue;
    private BlockingQueue<String> fileNameQueue;

    public EtlUserExecutor setInputData(ExecutorInputData executorInputData, BlockingQueue<Map<Integer, List<User>>> queue,
                                        BlockingQueue<String> fileNameQueue) {
        this.queue = queue;
        this.fileName = executorInputData.getFileName();
        this.fileNameQueue = fileNameQueue;
        return this;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Map<Integer, List<User>> processingData = queue.take();

                processingData.forEach((key, value) -> {
                    if (value != null && !value.isEmpty()) {
                        String fileNameWithKey = fileName + "_" + key;

                        try {
                            JsonConverter.convertAndStoreJsonFile(value, fileNameWithKey);
                            log.info(">>>Json file was converted with name {}", fileNameWithKey);
                            fileNameQueue.put(fileNameWithKey);
                        } catch (Exception ex) {
                            log.error("Error occurred while converting provided data to json file");
                            //TODO: keep failed data for retry process
                        }

                        try {
                            ParquetConverter.convertAndStoreParquetFile(value, fileNameWithKey);
                            log.info(">>>Parquet file was converted with name {}", fileNameWithKey);
                            fileNameQueue.put(fileNameWithKey);
                        } catch (Exception ex) {
                            log.error("Error occurred while converting provided data to parquet file");
                            //TODO: keep failed data for retry process
                        }
                    }
                });
            } catch (InterruptedException ex) {
                log.error("Error occurred while processing and converting data");
                //TODO: send informing notification to admins with failed data
            }
        }
    }
}
