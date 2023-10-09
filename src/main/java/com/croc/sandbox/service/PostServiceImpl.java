package com.croc.sandbox.service;

import com.croc.sandbox.dao.entity.PostEntity;
import com.croc.sandbox.dao.entity.TagEntity;
import com.croc.sandbox.dao.repository.PostRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.engine.spi.EntityEntry;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author VBoychenko
 * @since 06.10.2023
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl {

    private final PostRepository postRepository;
    private final TagServiceImpl tagService;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public PostEntity newPost(String title) {
        PostEntity post = new PostEntity();
        post.setTitle(title);
        return postRepository.save(post);
    }

    @Transactional
    public void deletePostByTitle(String title) {
        postRepository.deleteByTitle(title);
    }

    @Transactional (readOnly = true)
    public PostEntity findByTitle(String title) throws Exception {
        PostEntity post = postRepository.findByTitle(title);

        org.hibernate.engine.spi.PersistenceContext persistenceContext = getHibernatePersistenceContext();

        if (!entityManager.contains(post)) {
            throw new Exception("Post was not found in context");
        }

        EntityEntry entityEntry = persistenceContext.getEntry(post);
        if (entityEntry.getLoadedState() != null) {
            throw new Exception("Post has loaded state");
        }

        return post;
    }

    @Transactional
    public PostEntity findById(UUID id) throws Exception {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(Exception::new);

        org.hibernate.engine.spi.PersistenceContext persistenceContext = getHibernatePersistenceContext();

        EntityEntry entityEntry = persistenceContext.getEntry(post);
        if (entityEntry.getLoadedState() == null) {
            throw new Exception("Post hasn't loaded state");
        }

        return post;
    }

    @Transactional
    public PostEntity newPostAndTag(String title) throws Exception {
        PostEntity post = new PostEntity();
        post.setTitle(title);
        var postEntity = postRepository.save(post);
        var tagEntity = new TagEntity();
        tagEntity.setTitle(title);

        try {
            tagService.mockTransactionalMethod(tagEntity);
        } catch (RuntimeException e) {
            log.info("Внутренняя транзакция сфейлилась");
        }
        return postEntity;
    }

    public void saveAndDetachEntity(PostEntity entity) {
        // Сохраним сущность
        entityManager.persist(entity);
        // Форсим сохранение изменений
        entityManager.flush();
        // Убираем сущность из контекста
        entityManager.detach(entity);
        entity.setTitle("Пробуем изменить сущность, наивно ожидая, что изменения закатятся в базу");
    }

    private org.hibernate.engine.spi.PersistenceContext getHibernatePersistenceContext() {
        SharedSessionContractImplementor session = entityManager.unwrap(
                SharedSessionContractImplementor.class
        );
        return session.getPersistenceContext();
    }
}
