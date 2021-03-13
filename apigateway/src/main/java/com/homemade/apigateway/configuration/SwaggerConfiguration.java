package com.homemade.apigateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket devideApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("notes-application")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.apigateway.rest"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo());
    }

    /**
     * Provides basic information about API.
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Notes API")
                .description("Notes REST API v1.")
                .termsOfServiceUrl("http://homemade-notes.com/")
                .contact(new Contact("Notes corp.", "http://homemade-notes.com/", "testik812@gmail.com"))
                .version("1.0")
                .build();
    }

}
