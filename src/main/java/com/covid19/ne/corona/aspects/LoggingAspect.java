package com.covid19.ne.corona.aspects;

/* sanjayda created on 4/4/2020 inside the package - com.covid19.ne.corona.aspects */

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Aspect
@Configuration
public class LoggingAspect {

    @Before("execution(* com.covid19.ne.corona.controller.*.*(..))")
    public void before(JoinPoint joinPoint) {
        //Advice
        HitCount.hit();
        log.info("API hitted ...");
        log.info(" Allowed execution for {}", joinPoint);
    }

}


