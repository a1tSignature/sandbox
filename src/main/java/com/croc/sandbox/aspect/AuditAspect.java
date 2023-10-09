package com.croc.sandbox.aspect;

import com.croc.sandbox.common.AuditNotification;
import com.croc.sandbox.service.AuditServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author VBoychenko
 * @since 09.10.2023
 */
@Component
@RequiredArgsConstructor
@Aspect
@Slf4j
public class AuditAspect {

    private final AuditServiceImpl auditService;

    @Pointcut ("@annotation(com.croc.sandbox.annotation.Audit)")
    public void auditPointcut() {}

    @Around("auditPointcut()")
    public Object auditAroundAdvice(ProceedingJoinPoint joinPoint) {
        Object originalObject = joinPoint.getArgs()[0];
        Object targetObject;

        try {
            targetObject = joinPoint.proceed();
            // Имитируем создание сущности аудита
            AuditNotification notification = new AuditNotification(originalObject, targetObject);
            auditService.save(notification);
        } catch (Throwable e) {
            // Имитируем обработку ошибки
            log.error("Произошла ошибка при выполнении аудируемого метода. Сущность аудита не создана");
            return null;
        }

        return targetObject;
    }
}
