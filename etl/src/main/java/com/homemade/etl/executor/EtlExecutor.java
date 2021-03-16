package com.homemade.etl.executor;


import com.homemade.etl.converter.JsonConverter;
import com.homemade.etl.converter.ParquetConverter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;


@Slf4j
public class EtlExecutor<T> implements Runnable {

    private final String fileName;
    private final BlockingQueue<Map<Integer, List<T>>> queue;

    public EtlExecutor(BlockingQueue<Map<Integer, List<T>>> queue, String fileName) {
        this.queue = queue;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Map<Integer, List<T>> processingData = queue.take();

                processingData.forEach((key, value) -> {
                    if (value != null && !value.isEmpty()) {
                        String fileNameWithKey = fileName + "_" + key;

                        try {
                            JsonConverter.convertAndStoreJsonFile(value, fileNameWithKey);
                            log.info(">>>Json file was converted with name {}", fileNameWithKey);
                        } catch (Exception ex) {
                            log.error("Error occurred while converting provided data to json file");
                            //TODO: keep failed data for retry process
                        }

                        try {
                            ParquetConverter.convertAndStoreParquetFile(value, fileNameWithKey);
                            log.info(">>>Parquet file was converted with name {}", fileNameWithKey);
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
