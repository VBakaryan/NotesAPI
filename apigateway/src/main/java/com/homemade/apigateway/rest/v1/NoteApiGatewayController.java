package com.homemade.apigateway.rest.v1;

import com.homemade.apigateway.client.v1.NoteApiClient;
import com.homemade.apigateway.domain.Note;
import com.homemade.apigateway.security.AuthorizationFacade;
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
public class NoteApiGatewayController {

    private final NoteApiClient noteApiClient;
    private final AuthorizationFacade authorizationFacade;
    @Autowired
    public NoteApiGatewayController(NoteApiClient noteApiClient, AuthorizationFacade authorizationFacade) {
        this.noteApiClient = noteApiClient;
        this.authorizationFacade = authorizationFacade;
    }

    @PostMapping
    public Note createNote(@RequestBody Note note) {
        Long requestingUserId = authorizationFacade.getCurrentUserID();

        return noteApiClient.createNote(requestingUserId, note);
    }

    @PutMapping(value = "/{id}")
    public Note updateNote(@PathVariable("id") Long id,
                           @RequestBody Note note) {
        Long requestingUserId = authorizationFacade.getCurrentUserID();

        return noteApiClient.updateNote(id, requestingUserId, note);
    }

    @GetMapping(value = "/{id}")
    public Note getNoteById(@PathVariable("id") Long id) {
        Long requestingUserId = authorizationFacade.getCurrentUserID();

        return noteApiClient.getNoteById(id, requestingUserId);
    }

    @GetMapping("/user")
    public List<Note> getNotesForUser(@RequestParam("userId") Long userId) {
        Long requestingUserId = authorizationFacade.getCurrentUserID();

        return noteApiClient.getNotesForUser(userId, requestingUserId);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteNoteById(@PathVariable("id") Long id) {
        Long requestingUserId = authorizationFacade.getCurrentUserID();

        noteApiClient.deleteNoteById(id, requestingUserId);
    }

}
