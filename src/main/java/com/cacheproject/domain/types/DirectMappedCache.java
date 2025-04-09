package com.cacheproject.domain.types;

import com.cacheproject.domain.cache.model.CacheSystem;
import com.cacheproject.domain.cache.policy.ReplacementStrategy;


public class DirectMappedCache extends CacheSystem {
    public DirectMappedCache(int setCount, int wordsPerLine, ReplacementStrategy strategy) {
        // Direct-Mapped: associativity = 1
        super(setCount, 1, wordsPerLine, strategy);
    }
}

