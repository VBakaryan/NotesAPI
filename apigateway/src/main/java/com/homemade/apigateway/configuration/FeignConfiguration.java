package com.homemade.apigateway.configuration;

import feign.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FeignConfiguration {

    @Bean
    Logger.Level feignLoggerLevel(@Value("${feign.client.config.default.loggerLevel}") String debugLevel) {
        return Logger.Level.valueOf(debugLevel);
    }

}
