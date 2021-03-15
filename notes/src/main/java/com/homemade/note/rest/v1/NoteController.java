package com.homemade.note.rest.v1;

import com.homemade.note.domain.Note;
import com.homemade.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/notes", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class NoteController {

    private final NoteService noteService;
    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    // region <APIs>

    @PostMapping
    public Note createNote(@RequestParam("userId") Long userId, @RequestBody Note note) {
        return noteService.createNote(userId, note);
    }

    @PutMapping(value = "/{id}")
    public void updateNote(@PathVariable("id") Long id,
                           @RequestBody Note note) {
        noteService.updateNote(id, note);
    }

    @GetMapping(value = "/{id}")
    public Note getNoteById(@PathVariable("id") Long id) {
        return noteService.getNoteById(id);
    }

    @GetMapping
    public List<Note> getNotesForUser(@RequestParam("userId") Long userId) {
        return noteService.getNotesForUser(userId);
    }

    @GetMapping(value = "/batch")
    public List<Note> getNotes(@RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "size", required = false) Integer size) {
        return noteService.getNotes(page, size);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteNoteById(@PathVariable("id") Long id) {
        noteService.deleteNoteById(id);
    }

    // endregion

}
