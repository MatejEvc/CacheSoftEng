package com.cacheproject.domain.types;

import com.cacheproject.domain.cache.model.CacheSystem;
import com.cacheproject.domain.cache.policy.ReplacementStrategy;

public class NWayAssociativeCache extends CacheSystem {
    public NWayAssociativeCache(int setCount, int associativity, int wordsPerLine, ReplacementStrategy strategy) {
        super(setCount, associativity, wordsPerLine, strategy);
    }
}

