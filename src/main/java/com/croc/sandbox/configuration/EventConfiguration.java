package com.croc.sandbox.configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * @author VBoychenko
 * @since 12.10.2023
 */
@Component
@RequiredArgsConstructor
public class EventConfiguration {

//    @Bean
//    public SimpleApplicationEventMulticaster applicationEventMulticaster() {
//        return new SimpleApplicationEventMulticaster();
//    }

//    @Bean
//    public EventListenerRemovePostProcessor eventListenerRemovePostProcessor() {
//        return new EventListenerRemovePostProcessor();
//    }
}
