package com.homemade.etl.repository;


import com.homemade.etl.domain.Note;

import java.util.Date;
import java.util.List;

public interface NoteRepository {

    List<Note> getAllNotes();

    List<Note> getRecentNotesWithPagination(int page, int size, Date from, Date to);

}
