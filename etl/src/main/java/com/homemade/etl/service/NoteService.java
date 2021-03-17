package com.homemade.etl.service;


import com.homemade.etl.domain.Note;

import java.util.Date;
import java.util.List;

public interface NoteService {

    List<Note> getAll();

    List<Note> getRecentWithPagination(int page, int size, Date from, Date to);

}
