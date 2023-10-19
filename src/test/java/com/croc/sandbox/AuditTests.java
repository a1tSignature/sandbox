package com.croc.sandbox;

import com.croc.sandbox.dao.entity.TagEntity;
import com.croc.sandbox.dao.repository.AuditRepository;
import com.croc.sandbox.service.TagServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.*;

/**
 * @author VBoychenko
 * @since 09.10.2023
 */
@SpringBootTest
@ActiveProfiles ("test")
@ComponentScan (basePackages = "com.croc.sandbox")
@Import ({SandboxTestConfiguration.class})
public class AuditTests {

    @Autowired
    private TagServiceImpl tagService;
    @Autowired
    private AuditRepository auditRepository;

    @Test
    // Аннотация для того, чтобы результат теста откатывался
    @Transactional
    @DisplayName ("Проверка на то, что аудирование сущности работает нормально")
    public void auditSuccessfulTest() {
        TagEntity entity = new TagEntity();
        entity.setTitle("title");

        // Сохраняем сущность с помощью метода, подлежащего аудированию
        var savedEntity = tagService.save(entity);
        // Проверяем, что сущность сохранилась в надлежащем виде
        assertEquals("Title", "title", savedEntity.getTitle());

        // Проверяем, что сущность аудита сохранилась.
        assertTrue("Audit entity", auditRepository.findAll().size() > 0);

    }

    @Test
    // Аннотация для того, чтобы результат теста откатывался
    @Transactional
    @DisplayName ("Проверка на то, что аспект аудита обрабатывает сфейлившуюся транзакцию с непроверяемым исключением")
    public void auditErrorTest() {
        TagEntity entity = new TagEntity();
        entity.setTitle("title");

        // Сохраняем сущность с помощью метода, подлежащего аудированию
        var savedEntity = tagService.saveWithError(entity);
        // Проверяем, что транзакция откатилась, сущность не сохранилась
        assertNull("entity", savedEntity);

        // Проверяем, что сущность аудита не создавалась.
        assertTrue("Audit entity", auditRepository.findAll().size() == 0);

    }

    @Test
    // Аннотация для того, чтобы результат теста откатывался
    @Transactional
    @DisplayName ("Проверка на то, что аспект аудита обрабатывает сфейлившуюся транзакцию с проверяемым исключением")
    public void auditExceptionTest() throws Exception {
        TagEntity entity = new TagEntity();
        // Создаем условия для выбрасывания исключения
        entity.setTitle("rollback");

        // Сохраняем сущность с помощью метода, подлежащего аудированию
        assertThrows(Throwable.class, () -> tagService.saveWithException(entity));

        // Проверяем, что сущность аудита не создавалась.
        assertTrue("Audit entity", auditRepository.findAll().size() == 0);

    }
}
