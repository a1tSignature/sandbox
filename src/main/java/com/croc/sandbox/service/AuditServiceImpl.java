package com.croc.sandbox.service;

import com.croc.sandbox.common.AuditNotification;
import com.croc.sandbox.dao.entity.AuditEntity;
import com.croc.sandbox.dao.repository.AuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author VBoychenko
 * @since 09.10.2023
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuditServiceImpl {

    private final AuditRepository auditRepository;

    @Transactional
    public AuditEntity save(AuditNotification notification) {
        // Имитируем конвертацию в сущность аудита
        AuditEntity newEntity = new AuditEntity();
        newEntity.setOriginalObject(notification.originalObject().toString());
        newEntity.setTargetObject(notification.resultObject().toString());

        // Сохраняем
        return auditRepository.save(newEntity);
    }
}
