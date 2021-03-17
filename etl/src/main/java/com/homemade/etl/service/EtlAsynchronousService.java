package com.homemade.etl.service;

import com.homemade.etl.common.utils.DateHelper;
import com.homemade.etl.domain.Note;
import com.homemade.etl.domain.User;
import com.homemade.etl.executor.EtlNoteExecutor;
import com.homemade.etl.executor.EtlUserExecutor;
import com.homemade.etl.executor.ExecutorInputData;
import com.homemade.etl.reader.EtlNoteReader;
import com.homemade.etl.reader.EtlUserReader;
import com.homemade.etl.reader.ReaderInputData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


@Slf4j
@Service
public class EtlAsynchronousService {

    @Value("${settings.job.etl.rowSize:#{1000}}")
    private int rowsSize;

    private final TaskExecutor taskExecutor;
    private final UserService userService;
    private final NoteService noteService;
    @Autowired
    public EtlAsynchronousService(@Qualifier("threadPoolTaskExecutor") TaskExecutor taskExecutor,
                                  UserService userService, NoteService noteService) {
        this.taskExecutor = taskExecutor;
        this.userService = userService;
        this.noteService = noteService;
    }

    ////////////////////////////

    public void executeEtlProcess(Date startDate, Date endDate) {
        BlockingQueue<Map<Integer, List<User>>> usersQueue = new LinkedBlockingDeque<>();
        BlockingQueue<Map<Integer, List<Note>>> notesQueue = new LinkedBlockingDeque<>();

        ReaderInputData readerInputData = ReaderInputData.builder().rowsSize(rowsSize).startTime(startDate).endTime(endDate).build();
        taskExecutor.execute(new EtlUserReader(userService).setInitialData(readerInputData, usersQueue));
        taskExecutor.execute(new EtlNoteReader(noteService).setInitialData(readerInputData, notesQueue));

        String formattedDate = DateHelper.getFormattedDate(endDate, DateHelper.DF_DATETIME);
        ExecutorInputData usersExecutorInputData = ExecutorInputData.builder().fileName("user_" + formattedDate).build();
        ExecutorInputData notesExecutorInputData = ExecutorInputData.builder().fileName("note_" + formattedDate).build();
        taskExecutor.execute(new EtlUserExecutor().setInputData(usersExecutorInputData, usersQueue));
        taskExecutor.execute(new EtlNoteExecutor().setInputData(notesExecutorInputData, notesQueue));
    }

}
