package com.homemade.apigateway.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class RequestResponseData {

    private String timestamp;

    private String remoteAddress;

    private String httpMethod;

    private String requestUri;

    private String requestParameters;

    private String handlerMethod;

    private long requestTime;

    private String remoteUser;

    private String userAgent;

    private String pathQuery;

    private String payload;

}
