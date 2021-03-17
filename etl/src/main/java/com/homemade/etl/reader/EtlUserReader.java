package com.homemade.etl.reader;

import com.homemade.etl.domain.User;
import com.homemade.etl.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;


@Slf4j
@Component
@SuppressWarnings("Duplicates")
@Scope("prototype")
public class EtlUserReader implements Runnable {

    private Date startTime;
    private Date endTime;
    private int rowsSize;
    private BlockingQueue<Map<Integer, List<User>>> queue;

    private UserService userService;
    @Autowired
    public EtlUserReader(UserService userService) {
        this.userService = userService;
    }

    public EtlUserReader setInitialData(ReaderInputData inputData, BlockingQueue<Map<Integer, List<User>>> queue) {
        this.endTime = inputData.getEndTime();
        this.startTime = inputData.getStartTime();
        this.rowsSize = inputData.getRowsSize();
        this.queue = queue;
        return this;
    }

    @Override
    public void run() {
        List<User> data;
        int pageNumber = 0;
        do {
            data = userService.getRecentWithPagination(pageNumber, rowsSize, startTime, endTime);

            Map<Integer, List<User>> mapData = new HashMap<>();
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
