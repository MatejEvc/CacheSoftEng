package com.cacheproject.domain.cache.policy;

import com.cacheproject.domain.cache.model.CacheLine;
import com.cacheproject.domain.cache.model.CacheSet;

import java.util.Random;

public class RandomStrategy implements ReplacementStrategy {
    private final Random random = new Random();

    @Override
    public CacheLine selectLineForReplacing(CacheSet set) {
        return set.getLine(random.nextInt(set.getLineCount()));
    }

    @Override
    public void updateAccessOrder(CacheSet set, CacheLine accessedLine) {
        // Keine Aktion benötigt
    }
}
