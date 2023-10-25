package com.croc.sandbox.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.lang.NonNull;

/**
 * @author VBoychenko
 * @since 26.10.2023
 */
public class ListenerRemovalBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // Динамически убираем бин из списка на инициализацию
        ((BeanDefinitionRegistry) beanFactory).removeBeanDefinition("asyncEventListener");
    }
}
