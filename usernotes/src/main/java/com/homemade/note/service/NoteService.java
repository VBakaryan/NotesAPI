package com.homemade.note.service;


import com.homemade.note.domain.Note;

import java.util.List;

public interface NoteService {

    Note createNote(Long userId, Note note);

    void updateNote(Long id, Note note, Long requestingUserId);

    Note getNoteById(Long id, Long requestingUserId);

    List<Note> getNotesForUser(Long requestingUserId);

    List<Note> getNotes(Integer page, Integer size, Long requestingUserId);

    void deleteNoteById(Long id, Long requestingUserId);
}
