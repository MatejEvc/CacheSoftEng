package com.cacheproject.domain.cache.policy;

import com.cacheproject.domain.cache.model.CacheLine;
import com.cacheproject.domain.cache.model.CacheSet;

public interface ReplacementStrategy {
    CacheLine selectLineForReplacing(CacheSet set);
    void updateAccessOrder(CacheSet set, CacheLine accessedLine);
}
