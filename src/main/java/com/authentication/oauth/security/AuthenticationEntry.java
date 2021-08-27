package com.authentication.oauth.security;

import com.authentication.oauth.common.constants.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class AuthenticationEntry implements AuthenticationEntryPoint {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.error("commence :: AuthenticationEntry : {}", e.getMessage());
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        handlerExceptionResolver.resolveException(httpServletRequest, httpServletResponse, null,
                new AccessDeniedException((AppConstants.ERROR_MSG_UNAUTHORIZED)));
    }
}
