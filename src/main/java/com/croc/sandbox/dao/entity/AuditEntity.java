package com.croc.sandbox.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

/**
 * @author VBoychenko
 * @since 09.10.2023
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table (name = "audit")
public class AuditEntity extends DomainObjectEntity{

    private String originalObject;

    private String targetObject;
}
