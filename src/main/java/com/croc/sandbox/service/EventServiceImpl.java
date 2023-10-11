package com.croc.sandbox.service;

import com.croc.sandbox.common.AsyncApplicationEvent;
import jakarta.servlet.AsyncEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @author VBoychenko
 * @since 11.10.2023
 */
@Service
@RequiredArgsConstructor
public class EventServiceImpl {
    private final ApplicationEventPublisher applicationEventPublisher;

    public String publishAsyncEvent(String message) {
        AsyncApplicationEvent event = new AsyncApplicationEvent(message);
        applicationEventPublisher.publishEvent(event);

        return message;
    }
}
