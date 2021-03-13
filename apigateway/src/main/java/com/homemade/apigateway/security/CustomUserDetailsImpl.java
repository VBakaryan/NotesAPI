package com.homemade.apigateway.security;

import com.homemade.apigateway.client.v1.UserApiClient;
import com.homemade.apigateway.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


@Slf4j
@Service(value = "userDetailsService")
public class CustomUserDetailsImpl implements UserDetailsService {
    private UserApiClient userApiClient;

    @Autowired
    public CustomUserDetailsImpl(UserApiClient usersAPIClient) {
        this.userApiClient = usersAPIClient;
    }

    @Override
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);

        String loginToLowerCase = login.toLowerCase();

        try {
            loginToLowerCase = URLDecoder.decode(loginToLowerCase, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            log.warn(String.format("Unable to decode login for authentication %s", login), e);
        }

        final User user = userApiClient.getUserByEmail(loginToLowerCase);
        if (user == null) {
            throw new UsernameNotFoundException("User " + loginToLowerCase + " was not found in the database");
        }

        return new UserPrincipal(user.getId(), user.getEmail(), user.getPassword(),
                    true, true, true, true, new ArrayList<>());
    }

}
