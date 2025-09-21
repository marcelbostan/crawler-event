package com.mb.crawler.catalog;

public interface VisitedPages {

    boolean isVisited(String url);

    boolean couldVisit(String url);
}
