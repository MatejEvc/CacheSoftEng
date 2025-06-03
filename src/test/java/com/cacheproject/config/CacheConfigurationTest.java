package com.cacheproject.config;

import com.cacheproject.domain.cache.policy.ReplacementStrategy;
import com.cacheproject.domain.provider.WordProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CacheConfigurationTest {

    @Test
    void constructor_and_getters_returnCorrectValues() {
        ReplacementStrategy mockStrategy = mock(ReplacementStrategy.class);
        WordProvider mockProvider = mock(WordProvider.class);

        CacheConfiguration config = new CacheConfiguration(
                8, // setCount
                2, // associativity
                4, // wordsPerLine
                mockStrategy,
                mockProvider
        );

        assertEquals(8, config.getSetCount());
        assertEquals(2, config.getAssociativity());
        assertEquals(4, config.getWordsPerLine());
        assertSame(mockStrategy, config.getReplacementStrategy());
        assertSame(mockProvider, config.getWordProvider());
    }

    @Test
    void differentInstances_withSameValues_areNotEqual() {
        ReplacementStrategy mockStrategy1 = mock(ReplacementStrategy.class);
        WordProvider mockProvider1 = mock(WordProvider.class);

        ReplacementStrategy mockStrategy2 = mock(ReplacementStrategy.class);
        WordProvider mockProvider2 = mock(WordProvider.class);

        CacheConfiguration config1 = new CacheConfiguration(4, 1, 8, mockStrategy1, mockProvider1);
        CacheConfiguration config2 = new CacheConfiguration(4, 1, 8, mockStrategy2, mockProvider2);

        assertNotEquals(config1, config2); // Default equals vergleicht Referenz
    }
}
