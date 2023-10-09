package com.croc.sandbox.dao.repository;

import com.croc.sandbox.dao.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author VBoychenko
 * @since 06.10.2023
 */
@Repository
public interface PostRepository extends JpaRepository<PostEntity, UUID> {

    PostEntity findByTitle(String title);

    void deleteByTitle(String title);
}
