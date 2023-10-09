package com.croc.sandbox.dao.repository;

import com.croc.sandbox.dao.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author VBoychenko
 * @since 06.10.2023
 */
@Repository
public interface TagRepository extends JpaRepository<TagEntity, UUID> {

    TagEntity findByTitle(String title);
}
