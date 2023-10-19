package com.croc.sandbox;

import com.croc.sandbox.dao.repository.TagRepository;
import com.croc.sandbox.service.EventServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
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
    @Autowired
    private SimpleApplicationEventMulticaster multicaster;

    @Test
    @Transactional
    @DisplayName ("Проверка на публикацию асинхронных событий")
    // Сделать ивент по завершению транзакции
    public void eventTest() throws InterruptedException {
        String eventMessage = "Текст события";
        // Запускаем публикацию события
        String msg = eventService.publishAsyncEvent(eventMessage);
        // Сразу после возврата из метода паблишера проверяем, есть ли созданный тег в базе
        assertNull("Проверка на null", tagRepository.findByTitle("Текст события new event"));
        // Ждем обозначенное время
        Thread.sleep(10000);
        // Снова проводим поиск
        assertNotNull("Проверка на не null", tagRepository.findByTitle("Текст события new event"));

    }

    @Test
    @Transactional
    @DisplayName ("Проверка на непубликацию события при откате транзакции")
    // Сделать ивент по завершению транзакции
    public void errorEventTest() {
        String eventMessage = "rollback";
        // Запускаем публикацию события
        try {
            String msg = eventService.publishAsyncFailureEvent(eventMessage);
        } catch (RuntimeException ex) {
            System.err.println("Отловлено исключение, которое откатывает транзакцию");
        }
        // Снова проводим поиск
        assertNull("Проверка на null", tagRepository.findByTitle("rollback"));

    }
}
