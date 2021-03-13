package com.homemade.apigateway.security;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
public class TokenController {

    @Resource(name = "tokenServices")
    private ConsumerTokenServices tokenServices;

    @Resource(name = "tokenStore")
    private TokenStore tokenStore;

    @RequestMapping(method = RequestMethod.POST, value = "/oauth/token/revokeById/{tokenId}")
    @ResponseBody
    public void revokeToken(HttpServletRequest request, @PathVariable("tokenId") String tokenId) {
        tokenServices.revokeToken(tokenId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tokens/client/{clientId}")
    @ResponseBody
    public List<String> getTokens(@PathVariable("clientId") String clientId) {
        Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientId(clientId);

        return Optional.ofNullable(tokens)
                .orElse(Collections.emptyList())
                .stream()
                .map(OAuth2AccessToken::getValue)
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/tokens/revokeRefreshToken/{tokenId:.*}")
    @ResponseBody
    public void revokeRefreshToken(@PathVariable("tokenId") String tokenId) {
        if (tokenStore instanceof JdbcTokenStore) {
            ((JdbcTokenStore) tokenStore).removeRefreshToken(tokenId);
        }
    }

}
