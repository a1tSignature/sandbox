package com.croc.sandbox.service;

import com.croc.sandbox.annotation.Audit;
import com.croc.sandbox.annotation.Log;
import com.croc.sandbox.dao.entity.PostEntity;
import com.croc.sandbox.dao.entity.TagEntity;
import com.croc.sandbox.dao.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author VBoychenko
 * @since 08.10.2023
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TagServiceImpl {
    private final TagRepository tagRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String mockTransactionalMethod(TagEntity tagEntity) {
        tagRepository.save(new TagEntity());
        if (!tagEntity.getTitle().isEmpty()) {
            throw new RuntimeException("Выброшу исключение, чтобы попробовать саботировать транзакцию");
        }

        return "Транзакция выполнилась успешно";
    }

    // Простой метод сохранения, успешный
    @Transactional
    @Audit
    public TagEntity save(TagEntity entity) {
        return tagRepository.save(entity);
    }


    // Метод сохранения, выбрасывающий непроверяемое исключение
    @Transactional
    @Audit
    public TagEntity saveWithError(TagEntity entity) {
        return tagRepository.save(null);
    }

    // Метод сохранения, выбрасывающий проверяемое исключение (не через JPA)
    // Добавляем транзакции возможность откатываться на проверяемых исключениях
    @Transactional(rollbackFor = Exception.class)
    @Audit
    public TagEntity saveWithException(TagEntity entity) throws Exception {
        var savedEntity = tagRepository.save(entity);
        if ("rollback".equals(entity.getTitle())) {
            throw new Exception("Сымитируем выброс проверяемого исключения не через JPA");
        }

        return savedEntity;
    }

    @Transactional (readOnly = true)
    @Log
    public TagEntity findByTitle(String title) {
        return tagRepository.findByTitle(title);
    }
}
