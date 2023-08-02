package com.pmurawski.app;

import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;

public class CacheService {

    private final CacheManager cacheManager;

    public CacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Scheduled(fixedRate = 3600000)
    public void clearCache() {
        cacheManager.getCache("currencies");
    }
}
