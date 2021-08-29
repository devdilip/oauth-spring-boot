package com.authentication.oauth.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* com.authentication.oauth.service.UserService.*(..))")
    public void beforeLogsForUserService(JoinPoint joinPoint){
        log.info("before :: LoggingAspect");
        log.info("log Before at method:- {} and args:- {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));

    }

    @After("execution(* com.authentication.oauth.service.UserService.*(..))")
    public void afterLogsForUserService(JoinPoint joinPoint){
        log.info("after :: LoggingAspect");
        log.info("log After at method:- {} and args:- {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "execution(* com.authentication.oauth.service.UserService.*(..))", returning = "result")
    public void afterReturningLogsForUserService(JoinPoint joinPoint, Object result){
        log.info("afterReturning :: LoggingAspect");
        log.info("log AfterReturning at method:- {} and args:- {}", joinPoint.getSignature().getName(), result);
    }

    @Pointcut("execution(* get*())")
    public void allGetters() {}

    @Pointcut("within(@org.springframework.stereotype.Repository *)")
    public void repositoryClassMethods() {
        log.info("springBeanPointCut :: LoggingAspect");
    }

    @Around("repositoryClassMethods()")
    public Object measureMethodExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        log.info("measureMethodExecutionTime :: LoggingAspect");
        return null;
    }

}
