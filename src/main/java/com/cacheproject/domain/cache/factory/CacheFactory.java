package com.cacheproject.domain.cache.factory;

import com.cacheproject.config.CacheConfiguration;
import com.cacheproject.domain.cache.model.Cache;
import com.cacheproject.domain.types.CacheType;
import java.util.Map;

public class CacheFactory {
    private static final Map<CacheType, CacheTypeFactory> factories = Map.of(
            CacheType.DIRECT_MAPPED, new DirectMappedCacheFactory(),
            CacheType.FULLY_ASSOCIATIVE, new FullyAssociativeCacheFactory(),
            CacheType.N_WAY, new NWayAssociativeCacheFactory()
    );

    public static Cache createCache(CacheType type, CacheConfiguration config) {
        if (type == null) {
            throw new IllegalArgumentException("Cache type is not allowed to be null");
        }

        CacheTypeFactory factory = factories.get(type);
        if (factory == null) {
            throw new IllegalArgumentException("Unsupported cache type: " + type);
        }

        return factory.createCache(config);
    }
}
