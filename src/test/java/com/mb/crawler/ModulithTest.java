package com.mb.crawler;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;


public class ModulithTest {

    @Test
    void verifiesModuleBoundaries() {
        ApplicationModules modules = ApplicationModules.of(CrawlerEventApplication.class);
        modules.verify();
    }

    @Test
    void generateComponentDiagram() {
        ApplicationModules modules = ApplicationModules.of(CrawlerEventApplication.class);

        new Documenter(modules)
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml();
    }
}
