package com.mb.crawler;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;


public class ModulithTest {

    @Test
    void verifiesModuleBoundaries() {
        ApplicationModules modules = ApplicationModules.of(CrawlerEventApplication.class);
        modules.verify();
    }
}
