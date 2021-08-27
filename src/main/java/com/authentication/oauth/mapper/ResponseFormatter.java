package com.authentication.oauth.mapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class ResponseFormatter {

    @Autowired
    private MessageSource messageSource;

}
