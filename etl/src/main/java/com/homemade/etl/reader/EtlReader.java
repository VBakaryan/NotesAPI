package com.homemade.etl.reader;

import com.homemade.etl.service.CommonService;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;


@Slf4j
public class EtlReader<T> implements Runnable {

    private final Date startTime;
    private final Date endTime;
    private final int rowsSize;
    private final BlockingQueue<Map<Integer, List<T>>> queue;
    private final CommonService<T> commonService;

    public EtlReader(CommonService<T> commonService, BlockingQueue<Map<Integer, List<T>>> queue,
                     Date startTime, Date endTime, int rowsSize) {
        this.commonService = commonService;
        this.queue = queue;
        this.startTime = startTime;
        this.endTime = endTime;
        this.rowsSize = rowsSize;
    }

    @Override
    public void run() {
        List<T> data;
        int pageNumber = 0;
        do {
            data = commonService.getRecentWithPagination(pageNumber, rowsSize, startTime, endTime);

            Map<Integer, List<T>> mapData = new HashMap<>();
            mapData.put(pageNumber, data);

            try {
                queue.put(mapData);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            pageNumber++;
        }
        while (data.size() == rowsSize);
    }

}
