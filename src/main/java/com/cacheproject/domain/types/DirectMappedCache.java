package com.cacheproject.domain.types;

import com.cacheproject.domain.cache.model.Cache;
import com.cacheproject.domain.cache.policy.ReplacementStrategy;
import com.cacheproject.domain.provider.WordProvider;


public class DirectMappedCache extends Cache {
    public DirectMappedCache(int setCount, int wordsPerLine, ReplacementStrategy strategy, WordProvider wordProvider) {
        // Direct-Mapped: associativity = 1
        super(setCount, 1, wordsPerLine, strategy, wordProvider);
    }
}

