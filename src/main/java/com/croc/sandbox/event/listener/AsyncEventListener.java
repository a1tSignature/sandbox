package com.croc.sandbox.event.listener;

import com.croc.sandbox.common.AsyncApplicationEvent;
import com.croc.sandbox.dao.entity.TagEntity;
import com.croc.sandbox.dao.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author VBoychenko
 * @since 11.10.2023
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AsyncEventListener {

    private final TagRepository tagRepository;

    @EventListener
    @Async
    public void handleSynchronizedApplicationEvent(AsyncApplicationEvent event) throws InterruptedException {
        log.info(String.format("Создан тэг с наименованием %s, пришедшим из асинхронного ивента", event.message()));
        Thread.sleep(10000);
        var newTag = new TagEntity(event.message());
        tagRepository.save(newTag);
    }
}
