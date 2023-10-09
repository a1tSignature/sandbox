package com.croc.sandbox;

import com.croc.sandbox.dao.entity.PostEntity;
import com.croc.sandbox.dao.entity.TagEntity;
import com.croc.sandbox.dao.repository.PostRepository;
import com.croc.sandbox.service.PostServiceImpl;
import com.croc.sandbox.service.TagServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * @author VBoychenko
 * @since 09.10.2023
 */
@SpringBootTest
@ActiveProfiles ("test")
@ComponentScan (basePackages = "com.croc.sandbox")
@Import ({SandboxTestConfiguration.class})
public class DetachTest {

    @Autowired
    private PostServiceImpl postService;
    @Autowired
    private PostRepository postRepository;

    @Test
    @Transactional
    @DisplayName ("Тестируем отвязывание сущности от контекста")
    public void detachTest() {
        PostEntity entity = new PostEntity();
        entity.setTitle("title");

        // Вызываем метод, который заменяет title сущности после вытаскивания ее из контекста хибера
        postService.saveAndDetachEntity(entity);

        // Проверяем, что сохранилась сущность с title, который мы задали изначально, а не с измененным
        var fetchedEntity = postRepository.findAll().get(0);
        assertEquals("Title", "title", fetchedEntity.getTitle());
    }
}
