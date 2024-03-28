package com.stepup.loggingapplication.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * Aspect class for logging method entry and exit in service classes.
 */
@Component
@EnableAspectJAutoProxy
@Aspect
public class LoggingAspect {

    private final Logger logger;

    /**
     * Constructor to initialize the logger.
     */
    public LoggingAspect() {
        this.logger = LogManager.getLogger(LoggingAspect.class);
    }

    /**
     * Advice method to log method entry.
     *
     * @param joinPoint The join point representing the intercepted method.
     */
    @Before("execution(* com.stepup.loggingapplication.service.*.*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        logger.info("Entering method: {}", joinPoint.getSignature().toShortString());
    }

    /**
     * Advice method to log method exit.
     *
     * @param joinPoint The join point representing the intercepted method.
     */
    @AfterReturning(pointcut = "execution(* com.stepup.loggingapplication.service.*.*(..))")
    public void logMethodExit(JoinPoint joinPoint) {
        logger.info("Exiting method: {}", joinPoint.getSignature().toShortString());
    }
}
