package com.cacheproject.domain.cache.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheAccessResultTest {

    @Test
    void constructor_and_getters_workForHit() {
        Word word = new Word(123);
        CacheAccessResult result = new CacheAccessResult(true, word);

        assertTrue(result.isHit());
        assertEquals(word, result.getWord());
    }

    @Test
    void constructor_and_getters_workForMiss() {
        Word word = new Word(0);
        CacheAccessResult result = new CacheAccessResult(false, word);

        assertFalse(result.isHit());
        assertEquals(word, result.getWord());
    }

    @Test
    void equals_and_hashCode_workForEqualObjects() {
        Word word = new Word(5);
        CacheAccessResult a = new CacheAccessResult(true, word);
        CacheAccessResult b = new CacheAccessResult(true, new Word(5));

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void toString_containsHitAndWord() {
        Word word = new Word(8);
        CacheAccessResult result = new CacheAccessResult(true, word);

        String str = result.toString();
        assertTrue(str.contains("true"));
        assertTrue(str.contains("8"));
    }
}
