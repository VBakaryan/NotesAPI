package com.homemade.apigateway.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


/**
 * Authentication facade providing base methods to retrieve
 * authenticated users and check for correct authorizations.
 */
@Slf4j
@Component
public class AuthorizationFacadeImpl implements AuthorizationFacade {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public UserPrincipal getCurrentUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null)
            return null;

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return (UserPrincipal) authentication.getPrincipal();
        }

        return null;
    }

    @Override
    public Long getCurrentUserID() {
        UserPrincipal user = getCurrentUser();
        return user == null ? null : user.getId();
    }

}