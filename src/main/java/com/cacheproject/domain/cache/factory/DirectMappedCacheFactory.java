package com.cacheproject.domain.cache.factory;

import com.cacheproject.config.CacheConfiguration;
import com.cacheproject.domain.cache.model.Cache;
import com.cacheproject.domain.types.DirectMappedCache;

public class DirectMappedCacheFactory implements CacheTypeFactory {
    @Override
    public Cache createCache(CacheConfiguration config) {
        return new DirectMappedCache(
            config.getSetCount(),
            config.getWordsPerLine(),
            config.getReplacementStrategy(),
            config.getWordProvider()
        );
    }
}
