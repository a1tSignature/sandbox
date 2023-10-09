package com.croc.sandbox.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author VBoychenko
 * @since 09.10.2023
 */
@Component
@Aspect
public class LogAspect {

    @Pointcut("@annotation(com.croc.sandbox.annotation.Log)")
    public void logPointcut(){
    }

    @Before ("logPointcut()")
    public void logAllMethodCallsAdvice(){
        System.out.println("In Aspect");
    }
}
