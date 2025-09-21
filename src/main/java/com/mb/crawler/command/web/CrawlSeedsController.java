package com.mb.crawler.command.web;

import com.mb.crawler.command.api.CrawlCommandApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crawl/runs")
@RequiredArgsConstructor
@Slf4j
public class CrawlSeedsController {

    public static final String DEFAULT_SEED = "http://books.toscrape.com";
    private final CrawlCommandApi crawlCommandApi;

    @PostMapping
    public ResponseEntity<Boolean> publish(@RequestParam(value = "seed", required = false) String seed) {
        String effectiveSeed = (seed == null || seed.isBlank()) ? DEFAULT_SEED : seed;
        boolean submitted = crawlCommandApi.submit(effectiveSeed);
        log.info("Crawl seed request: seed='{}', effective='{}', submitted={}", seed, effectiveSeed, submitted);
        return ResponseEntity.ok().body(submitted);
    }
}
