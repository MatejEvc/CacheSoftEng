package com.cacheproject.domain.types;

import com.cacheproject.domain.cache.model.Cache;
import com.cacheproject.domain.cache.policy.ReplacementStrategy;
import com.cacheproject.domain.provider.WordProvider;


public class FullyAssociativeCache extends Cache {
    public FullyAssociativeCache(int associativity, int wordsPerLine, ReplacementStrategy strategy, WordProvider wordProvider) {
        // Fully Associative: 1 Set, associativity = Anzahl der Cache-Lines
        super(1, associativity, wordsPerLine, strategy, wordProvider);
    }
}


