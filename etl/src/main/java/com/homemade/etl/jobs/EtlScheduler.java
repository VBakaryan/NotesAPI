package com.homemade.etl.jobs;


import com.homemade.etl.common.utils.DateHelper;
import com.homemade.etl.domain.Note;
import com.homemade.etl.domain.User;
import com.homemade.etl.executor.EtlExecutor;
import com.homemade.etl.reader.EtlReader;
import com.homemade.etl.service.impl.NoteServiceImpl;
import com.homemade.etl.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;


@Slf4j
@Component
public class EtlScheduler {

    @Value("${settings.job.etl.enabled:#{false}}")
    private boolean enabled;

    @Value("${settings.job.etl.threads:#{10}}")
    private int threads;

    @Value("${settings.job.etl.rowSize:#{1000}}")
    private int rowsSize;

    private final UserServiceImpl userService;
    private final NoteServiceImpl noteService;
    @Autowired
    public EtlScheduler(UserServiceImpl userService, NoteServiceImpl noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @Scheduled(cron = "${settings.job.etl.cron}")
    public void execute() {
        Date currentDate = new Date();
        String formattedDate = DateHelper.getFormattedDate(currentDate, DateHelper.DF_DATETIME);

        log.info(">>> ETL job started on {}", formattedDate);

        BlockingQueue<Map<Integer, List<User>>> usersQueue = new LinkedBlockingDeque<>();
        BlockingQueue<Map<Integer, List<Note>>> notesQueue = new LinkedBlockingDeque<>();

        if (enabled) {
            Date startOfDay = DateHelper.getDayStart(currentDate);

            // initializing Executor service
            ExecutorService executor = Executors.newFixedThreadPool(threads);

            executor.execute(new EtlReader<>(userService, usersQueue, startOfDay, currentDate, rowsSize));
            executor.execute(new EtlExecutor<>(usersQueue, "user_" + formattedDate));

            executor.execute(new EtlReader<>(noteService, notesQueue, startOfDay, currentDate, rowsSize));
            executor.execute(new EtlExecutor<>(notesQueue, "note_" + formattedDate));

            try {
                // finalizing service threads execution
                executor.shutdown();
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
                //TODO: send informing notification to admins about an issue
            }
        }

        log.info(">>> ETL job ended on {}", DateHelper.getFormattedDate(new Date(), DateHelper.DF_DATETIME));
    }

}
