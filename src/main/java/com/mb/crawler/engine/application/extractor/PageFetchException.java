package com.mb.crawler.engine.application.extractor;

public class PageFetchException extends RuntimeException {
    public PageFetchException(String message) {
        super(message);
    }

    public PageFetchException(String message, Throwable cause) {
        super(message, cause);
    }
}
