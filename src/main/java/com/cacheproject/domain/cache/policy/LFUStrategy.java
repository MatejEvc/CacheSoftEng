package com.cacheproject.domain.cache.policy;

import com.cacheproject.domain.cache.model.CacheLine;
import com.cacheproject.domain.cache.model.CacheSet;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class LFUStrategy implements ReplacementStrategy {
    private final Map<CacheSet, Map<CacheLine, Integer>> accessCounts = new HashMap<>();

    @Override
    public CacheLine selectLineForReplacing(CacheSet set) {
        Map<CacheLine, Integer> counts = accessCounts.getOrDefault(set, new HashMap<>());
        CacheLine minLine = null;
        int minCount = Integer.MAX_VALUE;
        for (int i = 0; i < set.getLineCount(); i++) {
            CacheLine line = set.getLine(i);
            int count = counts.getOrDefault(line, 0);
            if (minLine == null || count < minCount) {
                minLine = line;
                minCount = count;
            }
        }
        return minLine;
    }


    @Override
    public void updateAccessOrder(CacheSet set, CacheLine accessedLine) {
        Map<CacheLine, Integer> counts = accessCounts.computeIfAbsent(set, k -> new HashMap<>());
        counts.merge(accessedLine, 1, Integer::sum);
    }
}
