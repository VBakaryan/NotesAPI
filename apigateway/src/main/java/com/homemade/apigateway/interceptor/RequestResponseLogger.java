package com.homemade.apigateway.interceptor;


import com.homemade.apigateway.common.utils.JsonHelper;
import com.homemade.apigateway.domain.RequestResponseData;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Slf4j
@Component
@Aspect
public class RequestResponseLogger {
    private final static String USER_AGENT = "User-Agent";
    private final static List<String> SKIP_LOGGING_FOR_ENDPOINTS = Arrays.asList("/apigateway/healthcheck");
    private final static List<String> HTTP_METHODS_WITH_BODY = Arrays.asList(HttpMethod.POST.name(), HttpMethod.PUT.name());

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerExecution() {}

    @Around("restControllerExecution()")
    public Object logRequestResponseDetails(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        boolean skipLogging = SKIP_LOGGING_FOR_ENDPOINTS.contains(request.getRequestURI());

        String handlerMethod = getHandlerMethod(joinPoint);
        Map<Class, Object> bodyDetails = HTTP_METHODS_WITH_BODY.contains(request.getMethod()) ? getBodyDetails(joinPoint) : null;

        RequestResponseData requestResponseData = composeRequestResponseData(request, handlerMethod, bodyDetails);

        Instant startTime = Instant.now();
        Instant endTime;
        try {
            Object result = joinPoint.proceed();

            if (!skipLogging) {
                endTime = Instant.now();
                requestResponseData.setRequestTime(Duration.between(startTime, endTime).toMillis());

                log.info(JsonHelper.toJson(requestResponseData));
            }

            return result;
        } catch (Throwable t) {
            if (!skipLogging) {
                endTime = Instant.now();
                requestResponseData.setRequestTime(Duration.between(startTime, endTime).toMillis());

                log.info(JsonHelper.toJson(requestResponseData));
            }

            log.error("Request processing was failed with the error message [{}]", t.getMessage());

            throw t;
        }
    }

    private Map<Class, Object> getBodyDetails(ProceedingJoinPoint joinPoint) {
        MethodInvocationProceedingJoinPoint mpjp = (MethodInvocationProceedingJoinPoint) joinPoint;
        MethodSignature sig = (MethodSignature) mpjp.getSignature();

        Annotation[][] paramAnnotations = sig.getMethod().getParameterAnnotations();

        for (int i = 0; i < paramAnnotations.length; i++) {
            for (Annotation a : paramAnnotations[i]) {
                if (a.annotationType().getName().equals(RequestBody.class.getName())) {
                    Class bodyClassType = mpjp.getArgs()[i].getClass();
                    Object body = mpjp.getArgs()[i];

                    return Collections.singletonMap(bodyClassType, body);
                }
            }
        }

        return null;
    }

    private RequestResponseData composeRequestResponseData(HttpServletRequest request, String handlerMethod, Map<Class, Object> bodyDetails) {
        return RequestResponseData.builder()
                .httpMethod(request.getMethod())
                .remoteAddress(request.getRemoteAddr())
                .remoteUser(request.getRemoteUser())
                .userAgent(request.getHeader(USER_AGENT))
                .handlerMethod(handlerMethod)
                .pathQuery(getFullUri(request))
                .requestParameters(request.getQueryString())
                .timestamp(LocalDateTime.now().toString())
                .requestUri(request.getRequestURI())
                .payload(bodyDetails != null ? JsonHelper.toJson(bodyDetails) : null)
                .build();
    }

    private String getHandlerMethod(ProceedingJoinPoint joinPoint) {
        MethodInvocationProceedingJoinPoint mpjp = (MethodInvocationProceedingJoinPoint) joinPoint;
        MethodSignature sig = (MethodSignature) mpjp.getSignature();

        String className = sig.getDeclaringType().getName();
        String methodName = sig.getName();
        return className + "." + methodName;

    }

    private String getFullUri(HttpServletRequest request) {
        return request.getScheme() + "://" +
                request.getServerName() +
                ("http".equals(request.getScheme()) && request.getServerPort() == 80 || "https".equals(request.getScheme()) && request.getServerPort() == 443
                        ? "" : ":" + request.getServerPort() ) +
                request.getRequestURI() +
                (request.getQueryString() != null ? "?" + request.getQueryString() : "");
    }

}
