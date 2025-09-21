package com.mb.crawler.command.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class CommandConfig {

    @Bean(name = "commandExec")
    public ExecutorService commandExec() {
        return Executors.newFixedThreadPool(5);
    }
}
