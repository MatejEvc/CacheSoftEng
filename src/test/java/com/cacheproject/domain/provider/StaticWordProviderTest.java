package com.cacheproject.domain.provider;

import com.cacheproject.domain.cache.model.Word;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StaticWordProviderTest {

    @Test
    void provideWords_returnsArrayWithCorrectValue() {
        StaticWordProvider provider = new StaticWordProvider(17);
        Word[] words = provider.provideWords(4);

        assertNotNull(words);
        assertEquals(4, words.length);
        for (Word word : words) {
            assertEquals(new Word(17), word);
        }
    }

    @Test
    void provideWords_returnsEmptyArray_whenSizeIsZero() {
        StaticWordProvider provider = new StaticWordProvider(5);
        Word[] words = provider.provideWords(0);

        assertNotNull(words);
        assertEquals(0, words.length);
    }

    @Test
    void provideWords_handlesNegativeValue() {
        StaticWordProvider provider = new StaticWordProvider(-12);
        Word[] words = provider.provideWords(2);

        for (Word word : words) {
            assertEquals(new Word(-12), word);
        }
    }

    @Test
    void provideWords_returnsDifferentInstances() {
        StaticWordProvider provider = new StaticWordProvider(99);
        Word[] words = provider.provideWords(3);

        assertNotSame(words[0], words[1]);
        assertNotSame(words[1], words[2]);
    }

    @Test
    void provideWords_withLargeSize_doesNotThrow() {
        StaticWordProvider provider = new StaticWordProvider(1);
        assertDoesNotThrow(() -> provider.provideWords(1000));
    }
}
