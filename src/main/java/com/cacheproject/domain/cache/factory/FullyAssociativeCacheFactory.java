package com.cacheproject.domain.cache.factory;

import com.cacheproject.config.CacheConfiguration;
import com.cacheproject.domain.cache.model.Cache;
import com.cacheproject.domain.types.FullyAssociativeCache;

public class FullyAssociativeCacheFactory implements CacheTypeFactory {
    @Override
    public Cache createCache(CacheConfiguration config) {
        return new FullyAssociativeCache(
            config.getAssociativity(),
            config.getWordsPerLine(),
            config.getReplacementStrategy(),
            config.getWordProvider()
        );
    }
}
