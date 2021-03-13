package com.homemade.apigateway.rest.v1;

import com.homemade.apigateway.client.v1.UserApiClient;
import com.homemade.apigateway.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserApiGatewayController {

    private final UserApiClient userApiClient;
    @Autowired
    public UserApiGatewayController(UserApiClient userApiClient) {
        this.userApiClient = userApiClient;
    }


    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable("email") String email) {
        return userApiClient.getUserByEmail(email);
    }

}
