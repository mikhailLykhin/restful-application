package com.restful.app.logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
@Aspect
public class ServiceLogger {

    private static final Logger logger = LoggerFactory.getLogger(ServiceLogger.class);


    @AfterThrowing(pointcut = "execution(* com.restful.app.services.*.*(..))", throwing = "exception")
    public void logException(Exception exception) {
        logger.error(exception.getMessage());
    }

    @Around("execution(* com.restful.app.rest.controllers.*.*(..))")
    public Object logAroundControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        logger.info("Invoked method name: " + signature.getMethod().getName());
        Arrays.stream(joinPoint.getArgs())
                .forEach(o -> logger.info("Method arg value: " + o.toString()));
        logger.info(joinPoint.getSignature() + " executed in " + executionTime + "ms");
        return proceed;
    }

}
