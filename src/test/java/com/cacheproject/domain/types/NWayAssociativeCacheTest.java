package com.cacheproject.domain.types;

import com.cacheproject.domain.cache.model.Address;
import com.cacheproject.domain.cache.model.CacheAccessResult;
import com.cacheproject.domain.cache.policy.FIFOStrategy;
import com.cacheproject.domain.provider.StaticWordProvider;
import com.cacheproject.util.CacheTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NWayAssociativeCacheTest {

    private NWayAssociativeCache cache;

    @BeforeEach
    void setUp() {
        cache = (NWayAssociativeCache) CacheTestHelper.createTestCache(
                CacheType.N_WAY, 2, 2, 2, 7);
    }


    @Test
    void getSetCount_returnsCorrectValue() {
        assertEquals(2, cache.getSetCount());
    }

    @Test
    void getSet_returnsCorrectSet() {
        assertNotNull(cache.getSet(0));
        assertNotNull(cache.getSet(1));
        assertNotSame(cache.getSet(0), cache.getSet(1));
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
    void access_replacesLine_whenSetIsFull() {
        Address address1 = new Address(0b0000_0000);
        Address address2 = new Address(0b0010_0000);

        cache.access(address1);
        cache.access(address2);

        Address address3 = new Address(0b0100_0000);
        CacheAccessResult result = cache.access(address3);

        assertFalse(result.isHit());
    }

    @Test
    void access_returnsCorrectWord() {
        Address address = new Address(0b0000_0001);
        CacheAccessResult result = cache.access(address);
        assertEquals(new com.cacheproject.domain.cache.model.Word(7), result.getWord());
    }
}
