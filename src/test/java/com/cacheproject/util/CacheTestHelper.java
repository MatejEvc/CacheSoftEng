package com.cacheproject.util;

import com.cacheproject.config.CacheConfiguration;
import com.cacheproject.domain.cache.model.Cache;
import com.cacheproject.domain.cache.policy.FIFOStrategy;
import com.cacheproject.domain.provider.StaticWordProvider;
import com.cacheproject.domain.types.CacheType;
import com.cacheproject.domain.cache.factory.CacheFactory;

public class CacheTestHelper {
    public static Cache createTestCache(CacheType type, int sets, int associativity, int wordsPerLine, int staticValue) {
        CacheConfiguration config = new CacheConfiguration(
            sets, associativity, wordsPerLine,
            new FIFOStrategy(),
            new StaticWordProvider(staticValue)
        );
        return CacheFactory.createCache(type, config);
    }
}
