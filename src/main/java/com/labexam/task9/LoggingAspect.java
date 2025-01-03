package com.labexam.task9;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging method calls in the com.labexam.books package.
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Logs information before any method in com.labexam.books package is executed.
     *
     * @param joinPoint provides reflective access to the method being called
     */
    @Before("execution(* com.labexam.books.*.*(..))")
    public void logBeforeMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String targetClassName = joinPoint.getTarget().getClass().getSimpleName();
        logger.info("Method {} in class {} is about to be called", methodName, targetClassName);
        System.out.println("Method " + methodName + " in class " + targetClassName + " is about to be called");
    }
}
