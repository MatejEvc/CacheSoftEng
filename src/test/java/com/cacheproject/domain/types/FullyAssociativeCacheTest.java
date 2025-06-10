package com.cacheproject.domain.types;

import com.cacheproject.domain.cache.model.Address;
import com.cacheproject.domain.cache.model.CacheAccessResult;
import com.cacheproject.domain.cache.policy.FIFOStrategy;
import com.cacheproject.domain.provider.StaticWordProvider;
import com.cacheproject.util.CacheTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FullyAssociativeCacheTest {

    private FullyAssociativeCache cache;

    @BeforeEach
    void setUp() {
        cache = (FullyAssociativeCache) CacheTestHelper.createTestCache(
                CacheType.FULLY_ASSOCIATIVE, 1, 3, 2, 5);
    }


    @Test
    void getSetCount_isAlwaysOne() {
        assertEquals(1, cache.getSetCount());
    }

    @Test
    void access_returnsMissOnFirstAccess_andHitOnSecond() {
        Address address = new Address(0b0000_0000);
        CacheAccessResult missResult = cache.access(address);
        assertFalse(missResult.isHit());
        CacheAccessResult hitResult = cache.access(address);
        assertTrue(hitResult.isHit());
    }

    @Test
    void access_replacesLine_whenAllLinesFull() {
        cache.access(new Address(0b0000_0000)); // tag=0
        cache.access(new Address(0b0010_0000)); // tag=1
        cache.access(new Address(0b0100_0000)); // tag=2
        CacheAccessResult result = cache.access(new Address(0b0110_0000)); // tag=3
        assertFalse(result.isHit());
    }

    @Test
    void access_returnsCorrectWord() {
        Address address = new Address(0b0000_0001);
        CacheAccessResult result = cache.access(address);
        assertEquals(new com.cacheproject.domain.cache.model.Word(5), result.getWord());
    }
}
