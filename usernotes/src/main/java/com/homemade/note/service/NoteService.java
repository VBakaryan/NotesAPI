package com.homemade.note.service;


import com.homemade.note.domain.Note;

import java.util.List;

public interface NoteService {

    Note createNote(Long userId, Note note);

    void updateNote(Long id, Note note);

    Note getNoteById(Long id);

    List<Note> getNotesForUser(Long userId);

    List<Note> getNotes(Integer page, Integer size);

    void deleteNoteById(Long id);
}
