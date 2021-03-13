package com.homemade.apigateway.client.v1;

import com.homemade.apigateway.configuration.FeignConfiguration;
import com.homemade.apigateway.domain.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = "NoteAPIClient", path = "/api/v1/notes", url = "${microservices.notes.host}", configuration = FeignConfiguration.class)
public interface NoteApiClient {

    @PostMapping
    Note createNote(@RequestParam("userId") Long requestingUserId, @RequestBody Note note);

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Note> getNotesForUser(@RequestParam("userId") Long userId);

}
