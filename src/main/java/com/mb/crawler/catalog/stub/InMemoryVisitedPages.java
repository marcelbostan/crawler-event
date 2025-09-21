package com.mb.crawler.catalog.stub;

import com.mb.crawler.catalog.VisitedPages;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryVisitedPages implements VisitedPages {
    private final Set<String> visited = ConcurrentHashMap.newKeySet();

    public boolean couldVisit(String url) {
        if (url == null) {
            return false;
        }
        return visited.add(url);
    }
    public boolean isVisited(String url) {
        if (url == null) {
            return false;
        }
        return visited.contains(url);
    }
}
