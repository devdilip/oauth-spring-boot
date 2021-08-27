package com.authentication.oauth.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Pointcut("within(@org.springframework.stereotype.Repository *)")
    public void springBeanPointCut(){
        log.info("springBeanPointCut :: LoggingAspect");
    }
}
