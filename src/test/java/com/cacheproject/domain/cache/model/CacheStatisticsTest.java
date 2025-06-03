package com.cacheproject.domain.cache.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheStatisticsTest {

    private CacheStatistics stats;

    @BeforeEach
    void setUp() {
        stats = new CacheStatistics();
    }

    @Test
    void initialValues_areZero() {
        assertEquals(0, stats.getAccessCount());
        assertEquals(0, stats.getHitCount());
        assertEquals(0, stats.getMissCount());
        assertEquals(0, stats.getEvictionCount());
        assertEquals(0.0, stats.getHitRate());
        assertEquals(0.0, stats.getMissRate());
    }

    @Test
    void recordAccess_incrementsAccessAndHitOrMiss() {
        stats.recordAccess(true);
        stats.recordAccess(false);
        stats.recordAccess(true);

        assertEquals(3, stats.getAccessCount());
        assertEquals(2, stats.getHitCount());
        assertEquals(1, stats.getMissCount());
        assertEquals(2.0/3.0, stats.getHitRate());
        assertEquals(1.0/3.0, stats.getMissRate());
    }

    @Test
    void recordEviction_incrementsEvictionCount() {
        stats.recordEviction();
        stats.recordEviction();
        assertEquals(2, stats.getEvictionCount());
    }

    @Test
    void reset_setsAllCountsToZero() {
        stats.recordAccess(true);
        stats.recordAccess(false);
        stats.recordEviction();
        stats.reset();

        assertEquals(0, stats.getAccessCount());
        assertEquals(0, stats.getHitCount());
        assertEquals(0, stats.getMissCount());
        assertEquals(0, stats.getEvictionCount());
        assertEquals(0.0, stats.getHitRate());
        assertEquals(0.0, stats.getMissRate());
    }
}
