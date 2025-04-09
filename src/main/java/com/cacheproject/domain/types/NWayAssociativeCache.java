package com.cacheproject.domain.types;

import com.cacheproject.domain.cache.model.Cache;
import com.cacheproject.domain.cache.policy.ReplacementStrategy;
import com.cacheproject.domain.provider.WordProvider;

public class NWayAssociativeCache extends Cache {
    public NWayAssociativeCache(int setCount, int associativity, int wordsPerLine, ReplacementStrategy strategy, WordProvider wordProvider) {
        super(setCount, associativity, wordsPerLine, strategy, wordProvider);
    }
}

