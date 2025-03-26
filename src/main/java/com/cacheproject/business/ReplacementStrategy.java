package com.cacheproject.business;

public interface ReplacementStrategy {
    CacheLine selectLineForReplacing(CacheSet set);
    void updateAccessOrder(CacheSet set, CacheLine accessedLine);
}
