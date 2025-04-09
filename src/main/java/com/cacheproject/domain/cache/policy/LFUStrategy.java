package com.cacheproject.domain.cache.policy;

import com.cacheproject.domain.cache.model.CacheLine;
import com.cacheproject.domain.cache.model.CacheSet;

import java.util.HashMap;
import java.util.Map;

public class LFUStrategy implements ReplacementStrategy {
    private final Map<CacheSet, Map<CacheLine, Integer>> accessCounts = new HashMap<>();

    @Override
    public CacheLine selectLineForReplacing(CacheSet set) {
        return accessCounts.getOrDefault(set, new HashMap<>()).entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(set.getLine(0));
    }

    @Override
    public void updateAccessOrder(CacheSet set, CacheLine accessedLine) {
        Map<CacheLine, Integer> counts = accessCounts.computeIfAbsent(set, k -> new HashMap<>());
        counts.merge(accessedLine, 1, Integer::sum);
    }
}
