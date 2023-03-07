package com.example.productcategoryservice.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Pointcut(value = "within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointCut() {
        /* Method is empty as this is just a Pointcut,
         the implementations are in the advices*/
    }

    @After(value = "springBeanPointCut()")
    public void afterEndpoints(JoinPoint joinPoint) {
        String kind = joinPoint.getKind();
        System.out.println(kind + "test log");
    }

    @Before(value = "springBeanPointCut()")
    public void beforeEndpoints(JoinPoint joinPoint){
        System.out.println( "test log");
    }

}
