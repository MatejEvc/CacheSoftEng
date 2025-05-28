package com.cacheproject.domain.cache.policy;

import com.cacheproject.domain.cache.model.CacheLine;
import com.cacheproject.domain.cache.model.CacheSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FIFOStrategyTest {

    private FIFOStrategy fifoStrategy;
    private CacheSet mockSet;
    private CacheLine line1;
    private CacheLine line2;
    private CacheLine line3;

    @BeforeEach
    void setUp() {
        fifoStrategy = new FIFOStrategy();
        mockSet = mock(CacheSet.class);
        line1 = mock(CacheLine.class);
        line2 = mock(CacheLine.class);
        line3 = mock(CacheLine.class);

        when(mockSet.getLineCount()).thenReturn(3);
        when(mockSet.getLine(0)).thenReturn(line1);
        when(mockSet.getLine(1)).thenReturn(line2);
        when(mockSet.getLine(2)).thenReturn(line3);
    }

    @Test
    void selectLineForReplacing_returnsFirstInsertedLine_whenAllHaveDifferentInsertTimes() throws InterruptedException {
        fifoStrategy.updateAccessOrder(mockSet, line2);
        Thread.sleep(2);
        fifoStrategy.updateAccessOrder(mockSet, line1);
        Thread.sleep(2);
        fifoStrategy.updateAccessOrder(mockSet, line3);

        assertEquals(line2, fifoStrategy.selectLineForReplacing(mockSet));
    }

    @Test
    void selectLineForReplacing_returnsFirstLine_whenNoInsertions() {
        assertSame(line1, fifoStrategy.selectLineForReplacing(mockSet));
    }

    @Test
    void updateAccessOrder_onlySetsInsertionTimeOnce() {
        fifoStrategy.updateAccessOrder(mockSet, line1);
        long firstTime = getInsertionTime(mockSet, line1);

        fifoStrategy.updateAccessOrder(mockSet, line1);
        long secondTime = getInsertionTime(mockSet, line1);

        assertEquals(firstTime, secondTime);
    }

    @Test
    void insertionTimes_areISolatedPerCacheSet() {
        CacheSet anotherSet = mock(CacheSet.class);
        CacheLine anotherLine = mock(CacheLine.class);
        when(anotherSet.getLineCount()).thenReturn(1);
        when(anotherSet.getLine(0)).thenReturn(anotherLine);

        fifoStrategy.updateAccessOrder(mockSet, line1);
        fifoStrategy.updateAccessOrder(anotherSet, anotherLine);

        assertNotEquals(
                getInsertionTime(mockSet, line1),
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
        try {
            var field = FIFOTestHelper.getInsertionTimesField(fifoStrategy);
            var map = (java.util.Map<CacheSet, java.util.Map<CacheLine, Long>>) field.get(fifoStrategy);
            if (!map.containsKey(set)) return null;
            return map.get(set).get(line);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static class FIFOTestHelper {
        static java.lang.reflect.Field getInsertionTimesField(FIFOStrategy strategy) throws Exception {
            var field = FIFOStrategy.class.getDeclaredField("insertionTimes");
            field.setAccessible(true);
            return field;
        }
    }
}
