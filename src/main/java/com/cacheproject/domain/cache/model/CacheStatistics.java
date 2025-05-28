package com.cacheproject.domain.cache.model;

import java.util.concurrent.atomic.AtomicLong;

public class CacheStatistics {
    private final AtomicLong accessCount = new AtomicLong();
    private final AtomicLong hitCount = new AtomicLong();
    private final AtomicLong missCount = new AtomicLong();
    private final AtomicLong evictionCount = new AtomicLong();

    public void recordAccess(boolean hit) {
        accessCount.incrementAndGet();
        if (hit) {
            hitCount.incrementAndGet();
        } else {
            missCount.incrementAndGet();
        }
    }

    public void recordEviction() {
        evictionCount.incrementAndGet();
    }

    public long getAccessCount() {
        return accessCount.get();
    }

    public long getHitCount() {
        return hitCount.get();
    }

    public long getMissCount() {
        return missCount.get();
    }

    public long getEvictionCount() {
        return evictionCount.get();
    }

    public double getHitRate() {
        if (accessCount.get() == 0) return 0.0;
        return (double) hitCount.get() / accessCount.get();
    }

    public double getMissRate() {
        if (accessCount.get() == 0) return 0.0;
        return (double) missCount.get() / accessCount.get();
    }

    public void reset() {
        accessCount.set(0);
        hitCount.set(0);
        missCount.set(0);
        evictionCount.set(0);
    }
}
