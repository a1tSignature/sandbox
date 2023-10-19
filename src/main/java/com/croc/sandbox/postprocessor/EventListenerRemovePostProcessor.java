//package com.croc.sandbox.postprocessor;
//
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.context.event.ApplicationEventMulticaster;
//import org.springframework.context.event.EventListener;
//import org.springframework.context.event.SimpleApplicationEventMulticaster;
//import org.springframework.context.event.SmartApplicationListener;
//import org.springframework.core.task.SimpleAsyncTaskExecutor;
//import org.springframework.lang.NonNull;
//
///**
// * @author VBoychenko
// * @since 13.10.2023
// */
//public class EventListenerRemovePostProcessor implements BeanPostProcessor {
//
//    @Override
//    public Object postProcessAfterInitialization(@NonNull Object bean,
//                                                 @NonNull String beanName) {
//        if (bean instanceof SimpleApplicationEventMulticaster multicaster) {
//            multicaster.removeApplicationListeners(l -> l instanceof SmartApplicationListener &&
//                    ((SmartApplicationListener) l).getListenerId().startsWith("com.croc.sandbox.event.old.listener"));
//            multicaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
//
//
//            return multicaster;
//        } else {
//            return bean;
//        }
//    }
//
//}
