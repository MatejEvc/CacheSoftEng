package com.cacheproject.domain.types;

import com.cacheproject.domain.cache.model.CacheSystem;
import com.cacheproject.domain.cache.policy.ReplacementStrategy;


public class FullyAssociativeCache extends CacheSystem {
    public FullyAssociativeCache(int associativity, int wordsPerLine, ReplacementStrategy strategy) {
        // Fully Associative: 1 Set, associativity = Anzahl der Cache-Lines
        super(1, associativity, wordsPerLine, strategy);
    }
}


