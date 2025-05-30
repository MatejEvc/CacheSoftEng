package com.cacheproject.domain.cache.policy;

import com.cacheproject.domain.cache.model.CacheLine;
import com.cacheproject.domain.cache.model.CacheSet;
import com.cacheproject.domain.cache.policy.LRUStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Map;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
@org.junit.jupiter.api.extension.ExtendWith(MockitoExtension.class)
class LRUStrategyTest {

    private LRUStrategy lruStrategy;

    @Mock
    private CacheSet mockSet;

    @Mock
    private CacheLine line1, line2, line3;

    @BeforeEach
    void setUp() {
        lruStrategy = new LRUStrategy();
        when(mockSet.getLineCount()).thenReturn(3);
        lenient().when(mockSet.getLine(0)).thenReturn(line1);
        lenient().when(mockSet.getLine(1)).thenReturn(line2);
        lenient().when(mockSet.getLine(2)).thenReturn(line3);
    }

    @Test
    void selectLineForReplacing_returnsLeastRecentlyUsed() {
        lruStrategy.updateAccessOrder(mockSet, line1); // älteste
        lruStrategy.updateAccessOrder(mockSet, line2);
        lruStrategy.updateAccessOrder(mockSet, line3); // neuste

        assertEquals(line1, lruStrategy.selectLineForReplacing(mockSet));
    }

    @Test
    void updateAccessOrder_refreshesTimestamp() throws InterruptedException {
        lruStrategy.updateAccessOrder(mockSet, line1);
        lruStrategy.updateAccessOrder(mockSet, line2);

        Thread.sleep(10);
        lruStrategy.updateAccessOrder(mockSet, line1);

        assertEquals(line2, lruStrategy.selectLineForReplacing(mockSet));
    }

    @Test
    void selectLineForReplacing_withNoAccessHistory_returnsFirstLine() {
        assertSame(line1, lruStrategy.selectLineForReplacing(mockSet));
    }

    @Test
    void accessOrder_isIsolatedBetweenCacheSets() {
        CacheSet anotherSet = mock(CacheSet.class);
        when(anotherSet.getLineCount()).thenReturn(1);
        CacheLine anotherLine = mock(CacheLine.class);
        when(anotherSet.getLine(0)).thenReturn(anotherLine);

        lruStrategy.updateAccessOrder(mockSet, line1);
        lruStrategy.updateAccessOrder(anotherSet, anotherLine);

        assertNotEquals(anotherLine, lruStrategy.selectLineForReplacing(mockSet));
        assertSame(anotherLine, lruStrategy.selectLineForReplacing(anotherSet));
    }

    @Test
    void withSingleLine_alwaysReturnsSameLine() {
        when(mockSet.getLineCount()).thenReturn(1);
        lruStrategy.updateAccessOrder(mockSet, line1);
        assertSame(line1, lruStrategy.selectLineForReplacing(mockSet));
    }

    @Test
    void whenNullLineUpdated_throwsException() {
        assertThrows(NullPointerException.class,
                () -> lruStrategy.updateAccessOrder(mockSet, null));
    }

    @Test
    void whenSwitchingCacheSets_maintainsSeparateOrder() {
        CacheSet anotherSet = mock(CacheSet.class);
        when(anotherSet.getLineCount()).thenReturn(1);
        CacheLine anotherLine = mock(CacheLine.class);
        when(anotherSet.getLine(0)).thenReturn(anotherLine);

        lruStrategy.updateAccessOrder(mockSet, line1);
        lruStrategy.updateAccessOrder(anotherSet, anotherLine);

        assertNotSame(anotherLine, lruStrategy.selectLineForReplacing(mockSet));
        assertSame(anotherLine, lruStrategy.selectLineForReplacing(anotherSet));
    }

    @Test
    void afterLongInactivity_preservesOrder() {
        lruStrategy.updateAccessOrder(mockSet, line1);
        lruStrategy.updateAccessOrder(mockSet, line2);

        lruStrategy.getAccessOrder().get(mockSet).replaceAll((k,v) -> v - 3_600_000_000_000L);

        assertSame(line1, lruStrategy.selectLineForReplacing(mockSet));
    }

    @Test
    void withConcurrentAccesses_handlesTimestampUpdates() {
        IntStream.range(0, 1000).parallel().forEach(i -> {
            lruStrategy.updateAccessOrder(mockSet, i % 2 == 0 ? line1 : line2);
        });

        assertNotNull(lruStrategy.selectLineForReplacing(mockSet));
    }
}
