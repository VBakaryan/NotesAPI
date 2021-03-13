package com.homemade.apigateway.security;

import org.springframework.security.core.Authentication;


public interface AuthorizationFacade {

    Authentication getAuthentication();

    UserPrincipal getCurrentUser();

    Long getCurrentUserID();

}
