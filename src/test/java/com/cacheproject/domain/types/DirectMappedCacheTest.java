package com.cacheproject.domain.types;

import com.cacheproject.domain.cache.model.Address;
import com.cacheproject.domain.cache.model.CacheAccessResult;
import com.cacheproject.domain.cache.policy.FIFOStrategy;
import com.cacheproject.domain.provider.StaticWordProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectMappedCacheTest {

    private DirectMappedCache cache;

    @BeforeEach
    void setUp() {
        cache = new DirectMappedCache(
                4,
                2,
                new FIFOStrategy(),
                new StaticWordProvider(11)
        );
    }

    @Test
    void getSetCount_returnsCorrectValue() {
        assertEquals(4, cache.getSetCount());
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
    void access_replacesLine_whenTagDiffers() {
        Address address1 = new Address(0b0000_0000); // setIndex=0, tag=0
        Address address2 = new Address(0b0010_0000); // setIndex=0, tag=1

        cache.access(address1); // Miss -> füllt
        CacheAccessResult missResult = cache.access(address2); // Miss -< ersetzt

        assertFalse(missResult.isHit());
    }

    @Test
    void access_returnsCorrectWord() {
        Address address = new Address(0b0000_0001);
        CacheAccessResult result = cache.access(address);
        assertEquals(new com.cacheproject.domain.cache.model.Word(11), result.getWord());
    }

    @Test
    void getSet_returnsCorrectSet() {
        assertNotNull(cache.getSet(0));
        assertNotNull(cache.getSet(1));
        assertNotNull(cache.getSet(2));
        assertNotNull(cache.getSet(3));
    }
}
