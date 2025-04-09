package com.cacheproject.domain.cache.factory;

import com.cacheproject.domain.cache.model.CacheSystem;
import com.cacheproject.domain.cache.policy.LRUStrategy;
import com.cacheproject.domain.cache.policy.ReplacementStrategy;
import com.cacheproject.domain.types.CacheType;
import com.cacheproject.domain.types.DirectMappedCache;
import com.cacheproject.domain.types.FullyAssociativeCache;
import com.cacheproject.domain.types.NWayAssociativeCache;

public class CacheFactory {
    public static CacheSystem createCache(
            CacheType type,
            int setCount,
            int associativity,
            int wordsPerLine,
            ReplacementStrategy strategy
    ) {
        return switch (type) {
            case DIRECT_MAPPED -> new DirectMappedCache(setCount, wordsPerLine, strategy);
            case FULLY_ASSOCIATIVE -> new FullyAssociativeCache(associativity, wordsPerLine, strategy);
            case N_WAY -> new NWayAssociativeCache(setCount, associativity, wordsPerLine, strategy);
            default -> throw new IllegalArgumentException("Unsupported cache type");
        };
    }
}