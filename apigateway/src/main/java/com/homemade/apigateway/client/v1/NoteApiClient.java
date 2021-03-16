package com.homemade.apigateway.client.v1;

import com.homemade.apigateway.configuration.FeignConfiguration;
import com.homemade.apigateway.domain.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = "NoteAPIClient", path = "/api/v1/notes", url = "${microservices.notes.host}", configuration = FeignConfiguration.class)
public interface NoteApiClient {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    Note createNote(@RequestParam("userId") Long requestingUserId, @RequestBody Note note);

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    Note updateNote(@PathVariable("id") Long id, @RequestParam("userId") Long requestingUserId, @RequestBody Note note);

    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    Note getNoteById(@PathVariable("id") Long id, @RequestParam("userId") Long requestingUserId);

    @GetMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Note> getNotesForUser(@RequestParam("userId") Long userId, @RequestParam("userId") Long requestingUserId);

    @DeleteMapping(value = "/{id}")
    void deleteNoteById(@PathVariable("id") Long id, @RequestParam("userId") Long requestingUserId);

}
