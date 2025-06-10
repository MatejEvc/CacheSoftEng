package com.cacheproject.domain.cache.factory;

import com.cacheproject.config.CacheConfiguration;
import com.cacheproject.domain.cache.model.Cache;
import com.cacheproject.domain.cache.policy.FIFOStrategy;
import com.cacheproject.domain.provider.StaticWordProvider;
import com.cacheproject.domain.types.DirectMappedCache;
import com.cacheproject.domain.types.FullyAssociativeCache;
import com.cacheproject.domain.types.NWayAssociativeCache;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheTypeFactoryTest {

    @Test
    void directMappedCacheFactory_createsDirectMappedCache() {
        CacheConfiguration config = new CacheConfiguration(4, 1, 2, new FIFOStrategy(), new StaticWordProvider(1));
        CacheTypeFactory factory = new DirectMappedCacheFactory();

        Cache cache = factory.createCache(config);

        assertTrue(cache instanceof DirectMappedCache);
    }

    @Test
    void fullyAssociativeCacheFactory_createsFullyAssociativeCache() {
        CacheConfiguration config = new CacheConfiguration(1, 4, 2, new FIFOStrategy(), new StaticWordProvider(1));
        CacheTypeFactory factory = new FullyAssociativeCacheFactory();

        Cache cache = factory.createCache(config);

        assertTrue(cache instanceof FullyAssociativeCache);
    }

    @Test
    void nWayAssociativeCacheFactory_createsNWayAssociativeCache() {
        CacheConfiguration config = new CacheConfiguration(2, 2, 2, new FIFOStrategy(), new StaticWordProvider(1));
        CacheTypeFactory factory = new NWayAssociativeCacheFactory();

        Cache cache = factory.createCache(config);

        assertTrue(cache instanceof NWayAssociativeCache);
    }
}
