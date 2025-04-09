package com.cacheproject.domain.cache.policy;

import com.cacheproject.domain.cache.model.CacheLine;
import com.cacheproject.domain.cache.model.CacheSet;

import java.util.HashMap;
import java.util.Map;

public class FIFOStrategy implements ReplacementStrategy {
    private final Map<CacheSet, Map<CacheLine, Long>> insertionTimes = new HashMap<>();

    @Override
    public CacheLine selectLineForReplacing(CacheSet set) {
        return insertionTimes.getOrDefault(set, new HashMap<>()).entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(set.getLine(0));
    }

    @Override
    public void updateAccessOrder(CacheSet set, CacheLine accessedLine) {
        Map<CacheLine, Long> times = insertionTimes.computeIfAbsent(set, k -> new HashMap<>());
        times.putIfAbsent(accessedLine, System.nanoTime()); // Nur beim ersten Einfügen speichern
    }
}
