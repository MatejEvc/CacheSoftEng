package com.cacheproject.domain.cache.repository;

import com.cacheproject.domain.cache.model.Cache;

import java.util.HashMap;
import java.util.Map;

public class InMemoryCacheRepository implements CacheRepository {
    private final Map<String, Cache> cacheMap = new HashMap<>();

    @Override
    public Cache findById(String cacheId) {
        return cacheMap.get(cacheId);
    }

    @Override
    public void save(String cacheId, Cache cache) {
        cacheMap.put(cacheId, cache);
    }
}