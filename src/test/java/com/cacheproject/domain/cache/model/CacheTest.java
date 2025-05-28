package com.cacheproject.domain.cache.model;

import com.cacheproject.domain.provider.WordProvider;
import com.cacheproject.domain.cache.policy.ReplacementStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CacheTest {

    @Mock
    private ReplacementStrategy mockStrategy;
    @Mock
    private WordProvider mockProvider;

    private Cache cache;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockProvider.provideWords(anyInt()))
                .thenAnswer(invocation -> {
                    int size = invocation.getArgument(0);
                    Word[] words = new Word[size];
                    for (int i = 0; i < size; i++) words[i] = new Word(i);
                    return words;
                });
        cache = new Cache(2, 2, 2, mockStrategy, mockProvider);
    }

    @Test
    void access_returnsHit_whenLineWithTagExistsAndValid() {
        Address address = new Address(0b0000_0000);
        CacheSet set = cache.getSet(0);
        CacheLine line = set.getLine(0);
        line.setTag(0);
        line.setValid(true);
        mockStrategy.updateAccessOrder(set, line);

        CacheAccessResult result = cache.access(address);

        assertTrue(result.isHit());
        assertEquals(0, result.getWord().getValue());
    }

    @Test
    void access_returnsMiss_whenNoLineWithTagExists() {
        Address address = new Address(0b0000_0100);
        CacheSet set = cache.getSet(1);
        set.getLine(0).setTag(1);
        set.getLine(0).setValid(true);

        CacheAccessResult result = cache.access(address);

        assertFalse(result.isHit());
        assertEquals(0, result.getWord().getValue());
    }

    @Test
    void access_fillsEmptyLineOnMiss() {
        Address address = new Address(0b0000_1000);
        CacheSet set = cache.getSet(0);
        set.getLine(0).setValid(false);
        set.getLine(1).setValid(true);
        set.getLine(1).setTag(1);

        CacheAccessResult result = cache.access(address);

        assertFalse(result.isHit());
        assertTrue(set.getLine(0).isValid());
        assertEquals(2, set.getLine(0).getTag());
    }

    @Test
    void access_replacesLineWhenNoEmptyLine() {
        Address address = new Address(0b0001_0000);
        CacheSet set = cache.getSet(0);
        set.getLine(0).setValid(true);
        set.getLine(0).setTag(1);
        set.getLine(1).setValid(true);
        set.getLine(1).setTag(2);

        when(mockStrategy.selectLineForReplacing(eq(set))).thenReturn(set.getLine(1));

        CacheAccessResult result = cache.access(address);

        assertFalse(result.isHit());
        assertTrue(set.getLine(1).isValid());
        assertEquals(4, set.getLine(1).getTag());
    }

    @Test
    void access_callsUpdateAccessOrderOnHit() {
        Address address = new Address(0b0000_0000);
        CacheSet set = cache.getSet(0);
        CacheLine line = set.getLine(0);
        line.setTag(0);
        line.setValid(true);

        cache.access(address);

        verify(mockStrategy, times(1)).updateAccessOrder(set, line);
    }

    @Test
    void access_callsUpdateAccessOrderOnMiss() {
        Address address = new Address(0b0000_0100);
        CacheSet set = cache.getSet(0);
        set.getLine(0).setValid(false);
        set.getLine(1).setValid(true);
        set.getLine(1).setTag(1);

        cache.access(address);

        verify(mockStrategy, atLeastOnce()).updateAccessOrder(eq(set), any(CacheLine.class));
    }

    @Test
    void getSet_returnsCorrectSet() {
        CacheSet set0 = cache.getSet(0);
        CacheSet set1 = cache.getSet(1);
        assertNotNull(set0);
        assertNotNull(set1);
        assertNotSame(set0, set1);
    }

    @Test
    void getStatistics_initiallyZero() {
        assertEquals(0, cache.getStatistics().getAccessCount());
        assertEquals(0, cache.getStatistics().getHitCount());
        assertEquals(0, cache.getStatistics().getMissCount());
        assertEquals(0, cache.getStatistics().getEvictionCount());
    }

    @Test
    void access_updatesStatisticsOnHitAndMiss() {
        Address address1 = new Address(0b0000_0000);
        Address address2 = new Address(0b0000_0000);

        cache.access(address1); // Miss -> füllt Line
        cache.access(address2); // Hit

        assertEquals(2, cache.getStatistics().getAccessCount());
        assertEquals(1, cache.getStatistics().getHitCount());
        assertEquals(1, cache.getStatistics().getMissCount());
    }
}
