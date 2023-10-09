package com.croc.sandbox.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

/**
 * @author VBoychenko
 * @since 06.10.2023
 */
@Configuration
@EnableJpaAuditing (auditorAwareRef = "auditorRef")
public class JpaConfiguration {

    /**
     * Правило заполнения поля user.
     *
     * @return Optional.of(" userName ")
     */
    @Bean
    public AuditorAware<String> auditorRef() {
        return () -> Optional.of("a1tSign");
    }
}
