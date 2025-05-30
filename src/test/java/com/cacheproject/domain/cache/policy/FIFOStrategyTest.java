package com.cacheproject.business;

import com.cacheproject.domain.cache.model.CacheLine;
import com.cacheproject.domain.cache.model.CacheSet;
import com.cacheproject.domain.cache.policy.FIFOStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FIFOStrategyTest {

    private FIFOStrategy fifoStrategy;
    private CacheSet set;
    private CacheLine line1;
    private CacheLine line2;
    private CacheLine line3;

    @BeforeEach
    void setUp() {
        fifoStrategy = new FIFOStrategy();
        set = mock(CacheSet.class);
        line1 = mock(CacheLine.class);
        line2 = mock(CacheLine.class);
        line3 = mock(CacheLine.class);

        when(set.getLineCount()).thenReturn(3);
        when(set.getLine(0)).thenReturn(line1);
        when(set.getLine(1)).thenReturn(line2);
        when(set.getLine(2)).thenReturn(line3);
    }

    @Test
    void selectLineForReplacing_returnsFirstInsertedLine_whenAllHaveDifferentInsertTimes() throws InterruptedException {
        fifoStrategy.updateAccessOrder(set, line2);
        Thread.sleep(2);
        fifoStrategy.updateAccessOrder(set, line1);
        Thread.sleep(2);
        fifoStrategy.updateAccessOrder(set, line3);

        assertSame(line2, fifoStrategy.selectLineForReplacing(set));
    }

    @Test
    void selectLineForReplacing_returnsFirstLine_whenNoInsertionsTracked() {
        assertSame(line1, fifoStrategy.selectLineForReplacing(set));
    }

    @Test
    void updateAccessOrder_onlySetsInsertionTimeOnce() throws InterruptedException {
        fifoStrategy.updateAccessOrder(set, line1);
        long firstTime = getInsertionTime(set, line1);

        Thread.sleep(2);
        fifoStrategy.updateAccessOrder(set, line1);
        long secondTime = getInsertionTime(set, line1);

        assertEquals(firstTime, secondTime);
    }

    @Test
    void insertionTimes_areIsolatedPerCacheSet() {
        CacheSet anotherSet = mock(CacheSet.class);
        CacheLine anotherLine = mock(CacheLine.class);
        when(anotherSet.getLineCount()).thenReturn(1);
        when(anotherSet.getLine(0)).thenReturn(anotherLine);

        fifoStrategy.updateAccessOrder(set, line1);
        fifoStrategy.updateAccessOrder(anotherSet, anotherLine);

        assertNotEquals(
                getInsertionTime(set, line1),
                getInsertionTime(anotherSet, anotherLine)
        );
    }

    @Test
    void selectLineForReplacing_returnsFirstLine_whenNoLinesTrackedForSet() {
        CacheSet newSet = mock(CacheSet.class);
        CacheLine newLine = mock(CacheLine.class);
        when(newSet.getLineCount()).thenReturn(1);
        when(newSet.getLine(0)).thenReturn(newLine);

        assertSame(newLine, fifoStrategy.selectLineForReplacing(newSet));
    }

    private Long getInsertionTime(CacheSet set, CacheLine line) {
        Map<CacheSet, Map<CacheLine, Long>> map = fifoStrategy.getInsertionTimes();
        if (!map.containsKey(set)) return null;
        return map.get(set).get(line);
    }
}
