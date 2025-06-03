package com.cacheproject.domain.provider;

import com.cacheproject.domain.cache.model.Word;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomWordProviderTest {

    @Test
    void provideWords_returnsArrayWithCorrectSize() {
        RandomWordProvider provider = new RandomWordProvider();
        Word[] words = provider.provideWords(5);

        assertNotNull(words);
        assertEquals(5, words.length);
    }

    @Test
    void provideWords_valuesAreInrange() {
        RandomWordProvider provider = new RandomWordProvider();
        Word[] words = provider.provideWords(20);

        for (Word word : words) {
            int value = word.getValue();
            assertTrue(value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE);
        }
    }

    @Test
    void provideWords_returnsDifferentValues() {
        RandomWordProvider provider = new RandomWordProvider();
        Word[] words = provider.provideWords(10);

        boolean allSame = true;
        int first = words[0].getValue();
        for (Word word : words) {
            if (word.getValue() != first) {
                allSame = false;
                break;
            }
        }
        assertFalse(allSame);
    }

    @Test
    void provideWords_zeroSize_returnsEmptyArray() {
        RandomWordProvider provider = new RandomWordProvider();
        Word[] words = provider.provideWords(0);
        assertNotNull(words);
        assertEquals(0, words.length);
    }
}
