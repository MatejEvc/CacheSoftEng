package com.cacheproject.util;

import com.cacheproject.domain.cache.model.Cache;
import com.cacheproject.domain.cache.model.CacheLine;
import com.cacheproject.domain.cache.model.CacheSet;
import com.cacheproject.domain.provider.StaticWordProvider;
import com.cacheproject.domain.cache.policy.FIFOStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheStateRendererTest {

    private CacheStateRenderer renderer;
    private Cache cache;

    @BeforeEach
    void setUp() {
        renderer = new CacheStateRenderer();
        //Cache mit 2 Sets, 2 Lines pro Set, 2 Words pro Line
        cache = new com.cacheproject.domain.cache.model.Cache(
                2, 2, 2,
                new FIFOStrategy(),
                new StaticWordProvider(1)
        );
        CacheSet set0 = cache.getSet(0);
        CacheSet set1 = cache.getSet(1);

        CacheLine line00 = set0.getLine(0);
        line00.setValid(true);
        line00.setTag(3);

        CacheLine line01 = set0.getLine(1);
        line01.setValid(false);

        CacheLine line10 = set1.getLine(0);
        line10.setValid(true);
        line10.setTag(7);

        CacheLine line11 = set1.getLine(1);
        line11.setValid(true);
        line11.setTag(1);
    }

    @Test
    void renderAsAscii_containsSetAndLineInfo() {
        String output = renderer.renderAsAscii(cache);

        assertTrue(output.contains("Cache State"));
        assertTrue(output.contains("Set"));
        assertTrue(output.contains("Line"));
        assertTrue(output.contains("Tag"));
        assertTrue(output.contains("011")); // Tag von line00 (3b)
        assertTrue(output.contains("111")); // Tag von line10 (7b)
        assertTrue(output.contains("001")); // Tag von line11 (1b)
        assertTrue(output.contains("true"));
        assertTrue(output.contains("false") || output.contains("-----"));
    }

    @Test
    void renderAsAscii_handlesEmptyCache() {
        Cache emptyCache = new com.cacheproject.domain.cache.model.Cache(
                1, 1, 1,
                new FIFOStrategy(),
                new StaticWordProvider(0)
        );
        String output = renderer.renderAsAscii(emptyCache);
        assertTrue(output.contains("Cache State"));
        assertTrue(output.contains("Set"));
        assertTrue(output.contains("Line"));
    }
}
