package com.cacheproject.business;

import com.cacheproject.domain.cache.model.CacheLine;
import com.cacheproject.domain.cache.model.CacheSet;
import com.cacheproject.domain.cache.policy.LRUStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class) // Enables Mockito annotations
class LRUStrategyTest {

    private LRUStrategy lruStrategy;

    @Mock
    private CacheSet mockSet;

    @Mock
    private CacheLine line1, line2, line3;

    @BeforeEach
    void setUp() {
        lruStrategy = new LRUStrategy();

        // Grundlegende Konfiguration
        when(mockSet.getLineCount()).thenReturn(3);

        // Nur für Tests, die diese Stubs benötigen
        lenient().when(mockSet.getLine(0)).thenReturn(line1);
        lenient().when(mockSet.getLine(1)).thenReturn(line2);
        lenient().when(mockSet.getLine(2)).thenReturn(line3);
    }

    @Test
    void selectLineForReplacing_returnsLeastRecentlyUsed() {
        // Simulate access order
        lruStrategy.updateAccessOrder(mockSet, line1); // Oldest
        lruStrategy.updateAccessOrder(mockSet, line2); // Middle
        lruStrategy.updateAccessOrder(mockSet, line3); // Most recent

        assertEquals(line1, lruStrategy.selectLineForReplacing(mockSet));
    }

    @Test
    void selectLineForReplacing_withEqualTimestamps_returnsFirstAdded() {
        // Force same timestamp
        lruStrategy.updateAccessOrder(mockSet, line1);
        lruStrategy.updateAccessOrder(mockSet, line2);

        // fallbeck to first line when timestamps match
        assertSame(mockSet.getLine(0), lruStrategy.selectLineForReplacing(mockSet));
    }

    @Test
    void updateAccessOrder_refreshesTimestamp() throws InterruptedException {
        lruStrategy.updateAccessOrder(mockSet, line1);
        lruStrategy.updateAccessOrder(mockSet, line2);

        // Warten, dann line1 erneut aktualisieren
        Thread.sleep(50);
        lruStrategy.updateAccessOrder(mockSet, line1);

        // line2 sollte jetzt sein
        CacheLine selected = lruStrategy.selectLineForReplacing(mockSet);
        assertEquals(line2, selected);
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
    }

    @Test
    void afterLongInactivity_preservesOrder() {
        lruStrategy.updateAccessOrder(mockSet, line1);
        lruStrategy.updateAccessOrder(mockSet, line2);

        // Simuliere 1 Stunde Inaktivität
        getAccessOrderForTesting().get(mockSet).replaceAll((k,v) -> v - 3_600_000_000_000L);

        assertSame(line1, lruStrategy.selectLineForReplacing(mockSet));
    }

    @Test
    void withSameTimestamp_returnsFirstInserted() {
        long fixedTime = System.nanoTime();
        // Reflection um Testdaten zu setzen
        getAccessOrderForTesting().put(mockSet, Map.of(
                line1, fixedTime,
                line2, fixedTime
        ));

        assertSame(line1, lruStrategy.selectLineForReplacing(mockSet));
    }


    @Test
    void withConcurrentAccesses_handlesTimestampUpdates() {
        IntStream.range(0, 1000).parallel().forEach(i -> {
            lruStrategy.updateAccessOrder(mockSet, i % 2 == 0 ? line1 : line2);
        });

        assertNotNull(lruStrategy.selectLineForReplacing(mockSet));
    }


    @SuppressWarnings("unchecked")
    private Map<CacheSet, Map<CacheLine, Long>> getAccessOrderForTesting() {
        try {
            Field field = lruStrategy.getClass().getDeclaredField("accessOrder");
            field.setAccessible(true);
            return (Map<CacheSet, Map<CacheLine, Long>>) field.get(lruStrategy);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
