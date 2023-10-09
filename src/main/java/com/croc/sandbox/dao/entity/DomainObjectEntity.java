package com.croc.sandbox.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

/**
 * @author VBoychenko
 * @since 06.10.2023
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
@FieldNameConstants
@EntityListeners (AuditingEntityListener.class)
public abstract class DomainObjectEntity {
    /**
     * Идентификатор.
     */
    @Id
    @Column (name = "id")
    @UuidGenerator
    private UUID id;

    /**
     * Количество изменений записи.
     */
    @Version
    @Builder.Default
    private Long version = -1L;

    /**
     * Дата и время создания (системные).
     */
    @CreatedDate
    @JsonFormat (pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant createdAt;

    /**
     * Пользователь создавший запись.
     */
    @CreatedBy
    private String createdUser;

    /**
     * Дата и время обновления (системные).
     */
    @LastModifiedDate
    @JsonFormat (pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant updatedAt;

    /**
     * Пользователь обновивший запись.
     */
    @LastModifiedBy
    private String updatedUser;
}
