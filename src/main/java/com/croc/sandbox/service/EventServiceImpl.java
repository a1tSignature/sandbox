package com.croc.sandbox.service;

import com.croc.sandbox.common.AsyncApplicationEvent;
import com.croc.sandbox.common.AsyncApplicationFailureEvent;
import jakarta.servlet.AsyncEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author VBoychenko
 * @since 11.10.2023
 */
@Service
@RequiredArgsConstructor
public class EventServiceImpl {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final SimpleApplicationEventMulticaster multicaster;
    public String publishAsyncEvent(String message) {
        multicaster.removeApplicationListeners(l -> l instanceof SmartApplicationListener &&
                ((SmartApplicationListener) l).getListenerId().startsWith("com.croc.sandbox.event.old.listener"));
        AsyncApplicationEvent event = new AsyncApplicationEvent(message);
        applicationEventPublisher.publishEvent(event);

        return message;
    }


    @Transactional
    public String publishAsyncFailureEvent(String message) {

        AsyncApplicationFailureEvent event = new AsyncApplicationFailureEvent(message);
        applicationEventPublisher.publishEvent(event);

        if ("rollback".equals(message)) {
            throw new RuntimeException();
        }

        return message;
    }
}
