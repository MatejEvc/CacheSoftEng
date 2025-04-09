package com.cacheproject.domain.cache.factory;

import com.cacheproject.config.CacheConfiguration;
import com.cacheproject.domain.cache.model.Cache;
import com.cacheproject.domain.types.CacheType;
import com.cacheproject.domain.types.DirectMappedCache;
import com.cacheproject.domain.types.FullyAssociativeCache;
import com.cacheproject.domain.types.NWayAssociativeCache;

public class CacheFactory {

    public static Cache createCache(
            CacheType type,
            CacheConfiguration config
    ) {
        return switch (type) {
            case DIRECT_MAPPED -> new DirectMappedCache(config.getSetCount(), config.getWordsPerLine(), config.getReplacementStrategy(), config.getWordProvider());
            case FULLY_ASSOCIATIVE -> new FullyAssociativeCache(config.getAssociativity(), config.getWordsPerLine(), config.getReplacementStrategy(), config.getWordProvider());
            case N_WAY -> new NWayAssociativeCache(config.getSetCount(), config.getAssociativity(), config.getWordsPerLine(), config.getReplacementStrategy(), config.getWordProvider());
            default -> throw new IllegalArgumentException("Unsupported cache type");
        };
    }

}