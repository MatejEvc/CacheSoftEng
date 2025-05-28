package com.cacheproject.domain.service;

import com.cacheproject.config.CacheConfiguration;
import com.cacheproject.domain.cache.factory.CacheFactory;
import com.cacheproject.domain.cache.model.Address;
import com.cacheproject.domain.cache.model.CacheAccessResult;
import com.cacheproject.domain.cache.model.Cache;
import com.cacheproject.domain.cache.model.CacheStatistics;
import com.cacheproject.domain.cache.repository.CacheRepository;
import com.cacheproject.domain.types.CacheType;

public class CacheService {

    private final CacheRepository cacheRepository;

    public CacheService(CacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
    }

    public void createCache(String cacheId, CacheType cacheType, CacheConfiguration config) {
        Cache cache = CacheFactory.createCache(cacheType, config);
        cacheRepository.save(cacheId, cache);
    }

    public CacheAccessResult accessCache(String cacheId, int addressValue) {
        Cache cache = cacheRepository.findById(cacheId);
        if (cache == null) {
            throw new IllegalArgumentException("Cache with id " + cacheId + " not found.");
        }
        Address address = new Address(addressValue);
        return cache.access(address);
    }

    public Cache getCache(String cacheId) {
        return cacheRepository.findById(cacheId);

    }

    public CacheStatistics getStatistics(String cacheId){
        Cache cache = cacheRepository.findById(cacheId);
        if (cache == null) {
            throw new IllegalArgumentException("Cache whit id " + cacheId + " not found!");
        }
        return cache.getStatistics();
    }
}
