package com.croc.sandbox;

import com.croc.sandbox.dao.repository.TagRepository;
import com.croc.sandbox.service.EventServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.util.AssertionErrors.*;

/**
 * @author VBoychenko
 * @since 11.10.2023
 */
@SpringBootTest
@ActiveProfiles ("test")
@ComponentScan (basePackages = "com.croc.sandbox")
@Import ({SandboxTestConfiguration.class})
public class AsyncEventTest {

    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private TagRepository tagRepository;

    @Test
    @Transactional
    @DisplayName ("Проверка на публикацию асинхронных событий")
    public void eventTest() throws InterruptedException {
        String eventMessage = "Текст события";
        // Запускаем публикацию события
        String msg = eventService.publishAsyncEvent(eventMessage);
        // Сразу после возврата из метода паблишера проверяем, есть ли созданный тег в базе
        assertNull("Проверка на null", tagRepository.findByTitle("Текст события"));
        // Ждем обозначенное время
        Thread.sleep(10000);
        // Снова проводим поиск
        assertNotNull("Проверка на не null", tagRepository.findByTitle("Текст события"));

    }
}
