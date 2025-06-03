package com.cacheproject.domain.cache.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordTest {

    @Test
    void constructor_setsValueCorrectly() {
        Word word = new Word(42);
        assertEquals(42, word.getValue());
    }

    @Test
    void equals_returnsTrueForSameValue() {
        Word word1 = new Word(17);
        Word word2 = new Word(17);
        assertEquals(word1, word2);
    }

    @Test
    void equals_returnsFalseForDifferentValues() {
        Word word1 = new Word(5);
        Word word2 = new Word(6);
        assertNotEquals(word1, word2);
    }

    @Test
    void hashCode_sameForEqualWords() {
        Word word1 = new Word(99);
        Word word2 = new Word(99);
        assertEquals(word1.hashCode(), word2.hashCode());
    }

    @Test
    void toString_containsValue() {
        Word word = new Word(-12);
        String str = word.toString();
        assertTrue(str.contains("-12"));
    }
}
