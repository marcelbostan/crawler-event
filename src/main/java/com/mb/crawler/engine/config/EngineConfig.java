package com.mb.crawler.engine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class EngineConfig {

    @Bean(name = "engineExec")
    public ExecutorService engineExec() {
        return Executors.newFixedThreadPool(20);
    }
}
