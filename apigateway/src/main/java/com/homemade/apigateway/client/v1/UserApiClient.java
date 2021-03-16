package com.homemade.apigateway.client.v1;

import com.homemade.apigateway.configuration.FeignConfiguration;
import com.homemade.apigateway.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "UserAPIClient", path = "/api/v1/users", url = "${microservices.notes.host}", configuration = FeignConfiguration.class)
public interface UserApiClient {

    @GetMapping(path = "/email/{email}", consumes = MediaType.APPLICATION_JSON_VALUE)
    User getUserByEmail(@PathVariable("email") String email);

}
