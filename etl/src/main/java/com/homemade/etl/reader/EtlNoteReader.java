package com.homemade.etl.reader;

import com.homemade.etl.domain.Note;
import com.homemade.etl.service.NoteService;
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
public class EtlNoteReader implements Runnable {

    private Date startTime;
    private Date endTime;
    private int rowsSize;
    private BlockingQueue<Map<Integer, List<Note>>> queue;

    private NoteService noteService;
    @Autowired
    public EtlNoteReader(NoteService noteService) {
        this.noteService = noteService;
    }

    public EtlNoteReader setInitialData(ReaderInputData inputData, BlockingQueue<Map<Integer, List<Note>>> queue) {
        this.endTime = inputData.getEndTime();
        this.startTime = inputData.getStartTime();
        this.rowsSize = inputData.getRowsSize();
        this.queue = queue;
        return this;
    }

    @Override
    public void run() {
        List<Note> data;
        int pageNumber = 0;
        do {
            data = noteService.getRecentWithPagination(pageNumber, rowsSize, startTime, endTime);

            Map<Integer, List<Note>> mapData = new HashMap<>();
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
