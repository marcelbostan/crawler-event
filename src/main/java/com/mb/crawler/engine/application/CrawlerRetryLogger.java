package com.mb.crawler.engine.application;

import com.mb.crawler.engine.application.extractor.NonRetriablePageFetchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CrawlerRetryLogger implements RetryListener {

    @Override
    public <T, E extends Throwable> void onError(RetryContext ctx, RetryCallback<T,E> cb, Throwable t) {
        if (t instanceof NonRetriablePageFetchException) return;
        log.info("Retry attempt {} failed: {}", ctx.getRetryCount(), t.toString());
    }

    @Override
    public <T, E extends Throwable> void close(
            RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        log.debug("Retry complete. attempts={}, lastException={}",
                context.getRetryCount(), (throwable == null ? "none" : throwable.toString()));
    }
}