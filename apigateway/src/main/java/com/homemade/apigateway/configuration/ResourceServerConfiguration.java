package com.homemade.apigateway.configuration;

import com.homemade.apigateway.security.CustomAuthenticationEntryPoint;
import com.homemade.apigateway.security.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;


@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Autowired
    public ResourceServerConfiguration(CustomAuthenticationEntryPoint customAuthenticationEntryPoint, CustomLogoutSuccessHandler customLogoutSuccessHandler) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customLogoutSuccessHandler = customLogoutSuccessHandler;
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .headers().frameOptions().disable()
            .and()
            .authorizeRequests().antMatchers("/api/**").authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .logout().logoutSuccessHandler(customLogoutSuccessHandler)
            .and()
            .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint)
            .accessDeniedHandler(oauthAccessDeniedHandler());
    }

    @Bean
    public OAuth2AccessDeniedHandler oauthAccessDeniedHandler() {
        return new OAuth2AccessDeniedHandler();
    }

}
