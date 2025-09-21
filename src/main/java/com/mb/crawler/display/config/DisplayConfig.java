package com.mb.crawler.display.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class DisplayConfig {
    @Bean(name = "displayExec")
    public ExecutorService displayExec() {
        return Executors.newFixedThreadPool(4);
    }
}
