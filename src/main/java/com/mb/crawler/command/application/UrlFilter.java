package com.mb.crawler.command.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@Slf4j
public class UrlFilter {
    @Value("${command.url-policy.accept.domain:books.toscrape.com}")
    private String acceptDomain;

    public boolean isSameDomain(String url) {
        try {
            String host = URI.create(url).getHost();
            if (host == null){
                log.debug("Rejecting URL with no host: {}", url);
                return false;
            }
            boolean match = host.equalsIgnoreCase(acceptDomain);
            if (!match) {
                log.debug("Rejecting cross-domain URL: {} (host: {}, expected: {})", url, host, acceptDomain);
            }
            return match;
        } catch (IllegalArgumentException e) {
            log.debug("Rejecting invalid URL: {}", url, e);
            return false;
        }
    }

    public boolean looksLikeWebPage(String url) {
        String lower = url.toLowerCase();
        //should be extracted in more meaningfully variables as rn it is a code smell
        return !(lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".png") ||
                lower.endsWith(".gif") || lower.endsWith(".svg") || lower.endsWith(".webp") ||
                lower.endsWith(".css") || lower.endsWith(".js")  ||
                lower.endsWith(".pdf") || lower.endsWith(".zip") || lower.endsWith(".rar") ||
                lower.endsWith(".mp4") || lower.endsWith(".avi") || lower.endsWith(".mp3"));
    }
}
