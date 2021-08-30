package com.authentication.oauth.common.aspect;

import com.authentication.oauth.model.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* com.authentication.oauth.service.UserService.*(..))")
    public void beforeLogsForUserService(JoinPoint joinPoint){
        log.info("log Before at method:- {} and args:- {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }

    @After("execution(* com.authentication.oauth.service.UserService.*(..))")
    public void afterLogsForUserService(JoinPoint joinPoint){
        log.info("log After at method:- {} and args:- {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "execution(* com.authentication.oauth.service.UserService.*(..))", returning = "result")
    public void afterReturningLogsForUserService(JoinPoint joinPoint, Object result){
        log.info("log AfterReturning at method:- {} and args:- {}", joinPoint.getSignature().getName(), result);
    }

    @Pointcut("within(com.authentication.oauth.controller.*)")
    public void controllerPointcut(){
        //   Controller pointcut:  Method is empty as this is just a Pointcut, the implementation are in the Around
    }

    @Pointcut("within(com.authentication.oauth.service.*)")
    public void servicePointcut(){
        //   Service pointcut: Method is empty as this is just a Pointcut, the implementation are in the Around
    }

    @AfterThrowing(pointcut = "controllerPointcut() && servicePointcut()", throwing = "throwable")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable throwable){
        log.error("Exception in {}. {} () with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), throwable);
    }

    @Around("controllerPointcut()")
    public Object logControllerAround(ProceedingJoinPoint joinPoint) throws Throwable{

        List<String> request = Arrays.stream(joinPoint.getArgs()).map(Object::toString).collect(Collectors.toList());
        log.info("Controller Request: {}. {} () with argument[s]: {}",
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                request);
        try {
            Object responseBody = ((ResponseEntity) joinPoint.proceed()).getBody();
            if(responseBody instanceof UserResponse){
                UserResponse response = (UserResponse) responseBody;
                log.info("Controller Response: {}. {} () with result : Status Code = {}, Status message = {}, Response = {}",
                        joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                        response.getStatus().getCode(), response.getStatus().getMessage(), response);
            }
            return responseBody;
        }catch (IllegalArgumentException exception){
            log.error("Controller: Illegal arguments: {} in {}. {} ()",
                    Arrays.toString(joinPoint.getArgs()), joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName());
            throw exception;
        }
    }

    @Around("servicePointcut()")
    public Object logServiceAround(ProceedingJoinPoint joinPoint) throws Throwable{

        List<String> request = Arrays.stream(joinPoint.getArgs()).map(Object::toString).collect(Collectors.toList());
        log.info("Service Request: {}. {} () with argument[s]: {}",
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                request);

        try {
            Object object = joinPoint.proceed();
            if(object instanceof UserResponse){
                UserResponse response = (UserResponse) object;
                log.info("Service Response: {}. {} () with result : Status Code = {}, Status message = {}, Response = {}",
                        joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                        response.getStatus().getCode(), response.getStatus().getMessage(), response);
            }
            return object;
        }catch (IllegalArgumentException exception){
            log.error("Service: Illegal arguments: {} in {}. {} ()",
                    Arrays.toString(joinPoint.getArgs()), joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName());
            throw exception;
        }
    }
}
