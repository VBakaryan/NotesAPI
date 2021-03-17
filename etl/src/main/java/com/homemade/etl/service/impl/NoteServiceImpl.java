package com.homemade.etl.service.impl;

import com.homemade.etl.domain.Note;
import com.homemade.etl.repository.NoteRepository;
import com.homemade.etl.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> getAll() {
        return noteRepository.getAllNotes();
    }

    @Override
    public List<Note> getRecentWithPagination(int page, int size, Date from, Date to) {
        return noteRepository.getRecentNotesWithPagination(page, size, from, to);
    }

}
