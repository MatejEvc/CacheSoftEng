package com.cacheproject.domain.cache.factory;

import com.cacheproject.config.CacheConfiguration;
import com.cacheproject.domain.cache.model.Cache;
import com.cacheproject.domain.cache.policy.FIFOStrategy;
import com.cacheproject.domain.provider.StaticWordProvider;
import com.cacheproject.domain.types.CacheType;
import com.cacheproject.domain.types.DirectMappedCache;
import com.cacheproject.domain.types.FullyAssociativeCache;
import com.cacheproject.domain.types.NWayAssociativeCache;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheFactoryTest {

    @Test
    void createCache_returnsDirectMappedCache_whenTypeIsDirectMapped() {
        CacheConfiguration config = new CacheConfiguration(8, 1, 4, new FIFOStrategy(), new StaticWordProvider(1));
        Cache cache = CacheFactory.createCache(CacheType.DIRECT_MAPPED, config);
        assertTrue(cache instanceof DirectMappedCache);
    }

    @Test
    void createCache_returnsFullyAssociativeCache_whenTypeIsFullyAssociative() {
        CacheConfiguration config = new CacheConfiguration(1, 8, 4, new FIFOStrategy(), new StaticWordProvider(2));
        Cache cache = CacheFactory.createCache(CacheType.FULLY_ASSOCIATIVE, config);
        assertTrue(cache instanceof FullyAssociativeCache);
    }

    @Test
    void createCache_returnsNWayAssociativeCache_whenTypeIsNWay() {
        CacheConfiguration config = new CacheConfiguration(4, 2, 4, new FIFOStrategy(), new StaticWordProvider(3));
        Cache cache = CacheFactory.createCache(CacheType.N_WAY, config);
        assertTrue(cache instanceof NWayAssociativeCache);
    }

    @Test
    void createCache_throwsException_whenTypeIsUnknown() {
        CacheConfiguration config = new CacheConfiguration(1, 1, 1, new FIFOStrategy(), new StaticWordProvider(0));
        assertThrows(IllegalArgumentException.class, () -> {
            CacheFactory.createCache(null, config);
        });
    }
}
