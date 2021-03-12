package com.homemade.note.service.impl;

import com.homemade.note.common.exception.NotFoundException;
import com.homemade.note.domain.Note;
import com.homemade.note.entity.NoteEntity;
import com.homemade.note.repository.NoteRepository;
import com.homemade.note.service.NoteService;
import com.homemade.note.service.UserService;
import com.homemade.note.service.mapper.NoteMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@Transactional(readOnly = true)
public class NoteServiceImpl implements NoteService {

    private final UserService userService;
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    @Autowired
    public NoteServiceImpl(UserService userService, NoteRepository noteRepository, NoteMapper noteMapper) {
        this.userService = userService;
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
    }

    // region <BUSINESS LOGIC>

    @Override
    @Transactional
    public Note createNote(Note note) {
        if (userService.exist(note.getUserId())) {
            NoteEntity noteEntity = noteMapper.map(note, NoteEntity.class);

            noteEntity = noteRepository.save(noteEntity);

            return noteMapper.map(noteEntity, Note.class);
        } else {
            throw new NotFoundException(String.format("User is not exist with id [%s]", note.getUserId()));
        }
    }

    @Override
    @Transactional
    public void updateNote(Long id, Note note) {
        if (noteRepository.existsById(id)) {
            noteRepository.updateNote(id, note.getTitle(), note.getNote());
        } else {
            throw new NotFoundException(String.format("Note is not exist with id [%s]", id));
        }
    }

    @Override
    public Note getNoteById(Long id) {
        Optional<NoteEntity> noteEntity = noteRepository.findById(id);

        if (noteEntity.isPresent()) {
            return noteMapper.map(noteEntity.get(), Note.class);
        } else {
            throw new NotFoundException(String.format("Note is not exist with id [%s]", id));
        }
    }

    @Override
    public List<Note> getNotesForUser(Long userId) {
        List<NoteEntity> notes = noteRepository.getNotesByUserId(userId);

        return noteMapper.mapAsList(notes, Note.class);
    }

    @Override
    @Transactional
    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
    }

    // endregion

}
