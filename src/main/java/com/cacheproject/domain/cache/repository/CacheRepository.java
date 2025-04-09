package com.cacheproject.domain.cache.repository;

import com.cacheproject.domain.cache.model.Cache;

public interface CacheRepository {
    Cache findById(String cacheId);
    void save(String cacheId, Cache cache);
}

