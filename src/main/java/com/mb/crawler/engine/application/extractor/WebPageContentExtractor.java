package com.mb.crawler.engine.application.extractor;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class WebPageContentExtractor {

    private static final Set<Integer> RETRYABLE_STATUS_CODES = Set.of(408, 429, 500, 502, 503, 504);
    public static final int TIMEOUT = 10_000;
    public static final int MAX_BODY_8_MB = 8_388_608;
    public static final String LINK_EXTRACTOR_1_0 = "LinkExtractor/1.0";

    public Set<String> extractLinks(String url) {
        try {
            var res = Jsoup.connect(url)
                    .userAgent(LINK_EXTRACTOR_1_0)
                    .timeout(TIMEOUT)
                    .maxBodySize(MAX_BODY_8_MB)
                    .ignoreContentType(true)
                    .followRedirects(true)
                    .execute();

            int status = res.statusCode();

            if (RETRYABLE_STATUS_CODES.contains(status)) {
                log.warn("Should retry for: {} because of response status code: {}", url, status);
                throw  new PageFetchException("Should retry parsing");
            }

            String ct = Optional.ofNullable(res.contentType()).orElse("").toLowerCase(Locale.ROOT);
            boolean isHtml = ct.startsWith("text/html") || ct.contains("xhtml");

            if (!isHtml) {
                log.warn("Skip non-HTML ({}): {}", ct, url);
                throw new NonRetriablePageFetchException("Unsupported content type " + ct + " for URL: " + url);
            }

            var doc = res.parse();

            return doc.select("a[href]").stream()
                    .map(a -> a.attr("abs:href"))
                    .filter(href -> !href.isBlank())
                    .filter(this::isHttpOrHttps)
                    .collect(Collectors.toSet());

        } catch (IOException e) {
            throw new PageFetchException("Failed to fetch " + url + ": " + e.getMessage(), e);
        }
    }

    private boolean isHttpOrHttps(String href) {
        var uri = URI.create(href.trim());
        var scheme = uri.getScheme();
        return "http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme);
    }
}
