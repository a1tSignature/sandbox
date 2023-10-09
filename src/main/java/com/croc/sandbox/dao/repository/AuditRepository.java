package com.croc.sandbox.dao.repository;

import com.croc.sandbox.dao.entity.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author VBoychenko
 * @since 09.10.2023
 */
public interface AuditRepository extends JpaRepository<AuditEntity, UUID> {
}
