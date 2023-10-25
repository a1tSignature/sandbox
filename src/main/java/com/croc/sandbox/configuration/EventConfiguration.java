package com.croc.sandbox.configuration;

import com.croc.sandbox.postprocessor.ListenerRemovalBeanFactoryPostProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author VBoychenko
 * @since 12.10.2023
 */
@Component
@RequiredArgsConstructor
public class EventConfiguration {

    @Bean
    public ListenerRemovalBeanFactoryPostProcessor listenerRemovalBeanFactoryPostProcessor() {
        return new ListenerRemovalBeanFactoryPostProcessor();
    }
}
