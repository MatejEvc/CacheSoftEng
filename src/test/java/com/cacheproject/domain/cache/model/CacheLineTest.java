package com.cacheproject.domain.cache.model;

import com.cacheproject.domain.provider.WordProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CacheLineTest {

    private WordProvider mockProvider;
    private CacheLine cacheLine;

    @BeforeEach
    void setUp() {
        mockProvider = mock(WordProvider.class);
        Word[] words = new Word[]{new Word(10), new Word(20), new Word(30)};
        when(mockProvider.provideWords(3)).thenReturn(words);
        cacheLine = new CacheLine(3, mockProvider);
    }

    @Test
    void newCacheLine_isNotValidAndTagIsMinusOne() {
        assertFalse(cacheLine.isValid());
        assertEquals(-1, cacheLine.getTag());
    }

    @Test
    void setValid_setsAndGetsValidFlag() {
        cacheLine.setValid(true);
        assertTrue(cacheLine.isValid());
        cacheLine.setValid(false);
        assertFalse(cacheLine.isValid());
    }

    @Test
    void setTag_setsAndGetsTag() {
        cacheLine.setTag(42);
        assertEquals(42, cacheLine.getTag());
    }

    @Test
    void getData_returnsCorrectWords() {
        Word[] data = cacheLine.getData();
        assertArrayEquals(new Word[]{new Word(10), new Word(20), new Word(30)}, data);
    }

    @Test
    void setData_replacesDataArray() {
        Word[] newData = new Word[]{new Word(1), new Word(2), new Word(3)};
        cacheLine.setData(newData);
        assertArrayEquals(newData, cacheLine.getData());
    }

    @Test
    void getWord_returnsWordAtGivenOffset() {
        assertEquals(new Word(10), cacheLine.getWord(0));
        assertEquals(new Word(20), cacheLine.getWord(1));
        assertEquals(new Word(30), cacheLine.getWord(2));
    }

    @Test
    void setWord_setsWordAtGivenOffset() {
        cacheLine.setWord(1, new Word(99));
        assertEquals(new Word(99), cacheLine.getWord(1));
    }
}
