package com.croc.sandbox.event.listener;

import com.croc.sandbox.common.AsyncApplicationEvent;
import com.croc.sandbox.common.AsyncApplicationFailureEvent;
import com.croc.sandbox.dao.entity.TagEntity;
import com.croc.sandbox.dao.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author VBoychenko
 * @since 12.10.2023
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class NewEventListener {

    private final TagRepository tagRepository;

    @EventListener
    @Async
    public void handleSynchronizedApplicationEvent(AsyncApplicationEvent event) throws InterruptedException {
        log.info(String.format("Замененный листенер получил наименование %s, пришедшее из асинхронного ивента", event.message()));
        Thread.sleep(10000);
        var newTag = new TagEntity(event.message() + " new event");
        tagRepository.save(newTag);
    }

    @TransactionalEventListener
    public void handleSynchronizedApplicationEvent(AsyncApplicationFailureEvent event) throws InterruptedException {
        log.info("Листенер получил событие отката транзакции");
        var newTag = new TagEntity(event.message() + " new event");
        tagRepository.save(newTag);
    }

}
