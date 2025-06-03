package com.cacheproject.domain.service;

import com.cacheproject.config.CacheConfiguration;
import com.cacheproject.domain.cache.model.Cache;
import com.cacheproject.domain.cache.model.CacheAccessResult;
import com.cacheproject.domain.cache.policy.ReplacementStrategy;
import com.cacheproject.domain.cache.repository.CacheRepository;
import com.cacheproject.domain.provider.WordProvider;
import com.cacheproject.domain.types.CacheType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CacheServiceTest {

    @Mock
    private CacheRepository cacheRepository;

    @InjectMocks
    private CacheService cacheService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCache_savesCacheInRepository() {
        CacheConfiguration config = mock(CacheConfiguration.class);
        when(config.getSetCount()).thenReturn(4);
        when(config.getAssociativity()).thenReturn(2);
        when(config.getWordsPerLine()).thenReturn(4);
        when(config.getReplacementStrategy()).thenReturn(mock(ReplacementStrategy.class));
        when(config.getWordProvider()).thenReturn(mock(WordProvider.class));

        cacheService.createCache("testCache", CacheType.N_WAY, config);

        verify(cacheRepository, times(1)).save(eq("testCache"), any(Cache.class));
    }

    @Test
    void accessCache_returnsCacheAccessResult_whenCacheExists() {
        Cache mockCache = mock(Cache.class);
        when(cacheRepository.findById("testCache")).thenReturn(mockCache);
        CacheAccessResult accessResult = new CacheAccessResult(true, null);
        when(mockCache.access(any())).thenReturn(accessResult);

        CacheAccessResult result = cacheService.accessCache("testCache", 123);

        assertNotNull(result);
        assertTrue(result.isHit());
        verify(cacheRepository, times(1)).findById("testCache");
        verify(mockCache, times(1)).access(any());
    }

    @Test
    void accessCache_throwsException_whenNoCache() {
        when(cacheRepository.findById("missingCache")).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cacheService.accessCache("missingCache", 123);
        });

        assertEquals("Cache with id missingCache not found.", exception.getMessage());
    }

    @Test
    void getCache_returnsCache() {
        Cache mockCache = mock(Cache.class);
        when(cacheRepository.findById("testCache")).thenReturn(mockCache);

        Cache cache = cacheService.getCache("testCache");

        assertNotNull(cache);
        assertEquals(mockCache, cache);
    }

    @Test
    void getCache_returnsNull_whenCacheNotExisting() {
        when(cacheRepository.findById("missingCache")).thenReturn(null);

        Cache cache = cacheService.getCache("missingCache");

        assertNull(cache);
    }
}
