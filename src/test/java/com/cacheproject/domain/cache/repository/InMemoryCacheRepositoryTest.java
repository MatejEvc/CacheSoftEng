package com.cacheproject.domain.cache.repository;

import com.cacheproject.domain.cache.model.Cache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InMemoryCacheRepositoryTest {

    private InMemoryCacheRepository repository;
    private Cache mockCache1;
    private Cache mockCache2;

    @BeforeEach
    void setUp() {
        repository = new InMemoryCacheRepository();
        mockCache1 = mock(Cache.class);
        mockCache2 = mock(Cache.class);
    }

    @Test
    void findById_returnsNull_whenNothingIsSaved() {
        assertNull(repository.findById("notExisting"));
    }

    @Test
    void save_and_findById_returnsSavedCache() {
        repository.save("cache1", mockCache1);
        Cache result = repository.findById("cache1");
        assertNotNull(result);
        assertSame(mockCache1, result);
    }

    @Test
    void save_overwritesExistingCache_withSameId() {
        repository.save("cache1", mockCache1);
        repository.save("cache1", mockCache2);
        Cache result = repository.findById("cache1");
        assertNotNull(result);
        assertSame(mockCache2, result);
    }

    @Test
    void findById_returnsNull_forUnknownId() {
        repository.save("cache1", mockCache1);
        assertNull(repository.findById("unknown"));
    }
}
