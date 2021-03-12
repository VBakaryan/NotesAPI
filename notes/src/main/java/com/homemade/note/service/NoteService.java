package com.homemade.note.service;


import com.homemade.note.domain.Note;

import java.util.List;

public interface NoteService {

    Note createNote(Note note);

    void updateNote(Long id, Note note);

    Note getNoteById(Long id);

    List<Note> getNotesForUser(Long userId);

    void deleteNoteById(Long id);

}
