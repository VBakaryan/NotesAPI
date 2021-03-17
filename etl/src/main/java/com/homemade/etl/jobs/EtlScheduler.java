package com.homemade.etl.jobs;


import com.homemade.etl.common.utils.DateHelper;
import com.homemade.etl.service.EtlAsynchronousService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;


@Slf4j
@Component
public class EtlScheduler {

    @Value("${settings.job.etl.enabled:#{false}}")
    private boolean enabled;

    private final EtlAsynchronousService etlAsynchronousService;
    @Autowired
    public EtlScheduler(EtlAsynchronousService etlAsynchronousService) {
        this.etlAsynchronousService = etlAsynchronousService;
    }

    @Scheduled(cron = "${settings.job.etl.cron}")
    public void execute() {
        Date currentDate = new Date();
        String formattedDate = DateHelper.getFormattedDate(currentDate, DateHelper.DF_DATETIME);

        log.info(">>> ETL job started on {}", formattedDate);

        if (enabled) {
            Date startOfDay = DateHelper.getDayStart(DateHelper.add(currentDate, Calendar.MINUTE, -10));

            etlAsynchronousService.executeEtlProcess(startOfDay, currentDate);
        }

        log.info(">>> ETL job ended on {}", DateHelper.getFormattedDate(new Date(), DateHelper.DF_DATETIME));
    }

}
