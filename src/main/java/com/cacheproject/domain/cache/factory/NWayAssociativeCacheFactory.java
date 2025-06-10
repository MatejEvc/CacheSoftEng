package com.cacheproject.domain.cache.factory;

import com.cacheproject.config.CacheConfiguration;
import com.cacheproject.domain.cache.model.Cache;
import com.cacheproject.domain.types.NWayAssociativeCache;

public class NWayAssociativeCacheFactory implements CacheTypeFactory {
    @Override
    public Cache createCache(CacheConfiguration config) {
        return new NWayAssociativeCache(
            config.getSetCount(),
            config.getAssociativity(),
            config.getWordsPerLine(),
            config.getReplacementStrategy(),
            config.getWordProvider()
        );
    }
}
