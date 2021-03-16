package com.homemade.note.service.impl;

import com.homemade.note.common.exception.NotFoundException;
import com.homemade.note.common.exception.ValidationException;
import com.homemade.note.domain.Note;
import com.homemade.note.entity.NoteEntity;
import com.homemade.note.repository.NoteRepository;
import com.homemade.note.service.NoteService;
import com.homemade.note.service.mapper.NoteMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@Transactional(readOnly = true)
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository, NoteMapper noteMapper) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
    }

    // region <BUSINESS LOGIC>

    @Override
    @Transactional
    public Note createNote(Long userId, Note note) {
        validateRequester(userId, note);

        NoteEntity noteEntity = noteMapper.map(note, NoteEntity.class);

        noteEntity = noteRepository.save(noteEntity);

        return noteMapper.map(noteEntity, Note.class);
    }

    @Override
    @Transactional
    public void updateNote(Long id, Note note, Long requestingUserId) {
        validateNoteData(note);

        Optional<NoteEntity> noteEntity = noteRepository.findById(id);
        if (noteEntity.isPresent()) {
            if (!requestingUserId.equals(noteEntity.get().getId())) {
                throw new ValidationException(String.format("Note [%s] does not belong to user [%s]", id, requestingUserId));
            }

            noteRepository.updateNote(id, note.getTitle(), note.getNote());
        } else {
            throw new NotFoundException(String.format("Note is not exist with id [%s]", id));
        }
    }

    @Override
    public Note getNoteById(Long id, Long requestingUserId) {
        Optional<NoteEntity> noteEntity = noteRepository.findById(id);

        if (noteEntity.isPresent()) {
            Note note = noteMapper.map(noteEntity.get(), Note.class);

            validateRequester(requestingUserId, note);

            return note;
        } else {
            throw new NotFoundException(String.format("Note is not exist with id [%s]", id));
        }
    }

    @Override
    public List<Note> getNotesForUser(Long requestingUserId) {
        List<NoteEntity> notes = noteRepository.getNotesByUserId(requestingUserId);

        return noteMapper.mapAsList(notes, Note.class);
    }

    @Override
    public List<Note> getNotes(Integer page, Integer size, Long requestingUserId) {
        List<NoteEntity> noteEntities;
        if (page != null && size != null) {
            noteEntities = noteRepository.getNotes(PageRequest.of(page, size)).getContent();
        } else {
            noteEntities = noteRepository.findAll();
        }

        return noteMapper.mapAsList(noteEntities, Note.class);
    }

    @Override
    @Transactional
    public void deleteNoteById(Long id, Long requestingUserId) {
        Optional<NoteEntity> noteEntity = noteRepository.findById(id);

        if (noteEntity.isPresent()) {
            Note note = noteMapper.map(noteEntity.get(), Note.class);

            validateRequester(requestingUserId, note);

            noteRepository.deleteById(id);
        } else {
            throw new NotFoundException(String.format("Note is not exist with id [%s]", id));
        }
    }

    // endregion

    private void validateRequester(Long userId, Note note) {
        if (userId != null && !userId.equals(note.getUserId())) {
            throw new ValidationException(String.format("Requester [%s] and actual user [%s] mismatch", userId, note.getUserId()));
        }
    }

    private void validateNoteData(Note note) {
        if (note != null) {
            if (note.getTitle() == null || note.getTitle().length() > 50) {
                throw new ValidationException(String.format("Provided note title is null or exceeds the limit, note id [%s]", note.getId()));
            }
            if (note.getNote() == null || note.getNote().length() > 1000) {
                throw new ValidationException(String.format("Provided note is null or exceeds the limit, note id [%s]", note.getId()));
            }
        } else {
            throw new ValidationException("Note data is null");
        }
    }

}
