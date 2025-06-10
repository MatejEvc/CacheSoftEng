package com.cacheproject.domain.cache.factory;

import com.cacheproject.config.CacheConfiguration;
import com.cacheproject.domain.cache.model.Cache;

public interface CacheTypeFactory {
    Cache createCache(CacheConfiguration config);
}
