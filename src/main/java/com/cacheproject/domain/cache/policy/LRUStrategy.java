package com.cacheproject.domain.cache.policy;

import com.cacheproject.domain.cache.model.CacheLine;
import com.cacheproject.domain.cache.model.CacheSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.VisibleForTesting;

public class LRUStrategy implements ReplacementStrategy {
    private final Map<CacheSet, Map<CacheLine, Long>> accessOrder = new HashMap<>();
    @Override
    public CacheLine selectLineForReplacing(CacheSet set) {
        Map<CacheLine, Long> setAccessOrder = accessOrder
                .computeIfAbsent(set, k -> new HashMap<>());
        return setAccessOrder.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(set.getLine(0));
    }

    @Override
    public void updateAccessOrder(CacheSet set, CacheLine accessedLine) {
        Objects.requireNonNull(set, "CacheSet cannot be null");
        Objects.requireNonNull(accessedLine, "CacheLine cannot be null");

        accessOrder.computeIfAbsent(set, k -> new HashMap<>())
                .put(accessedLine, System.nanoTime());
    }

    @VisibleForTesting
    public Map<CacheSet, Map<CacheLine, Long>> getAccessOrder() {
        return accessOrder;
    }



}
