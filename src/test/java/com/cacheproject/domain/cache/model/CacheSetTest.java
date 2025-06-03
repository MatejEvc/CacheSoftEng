package com.cacheproject.domain.cache.model;

import com.cacheproject.domain.provider.WordProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CacheSetTest {

    @Mock
    private WordProvider mockWordProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        //Array von vier Words mit Wert 0,1,2,3
        when(mockWordProvider.provideWords(anyInt()))
                .thenAnswer(invocation -> {
                    int size = invocation.getArgument(0);
                    Word[] words = new Word[size];
                    for (int i = 0; i < size; i++) words[i] = new Word(i);
                    return words;
                });
    }

    @Test
    void constructor_createsCorrectNumberOfLines() {
        CacheSet set = new CacheSet(3, 4, mockWordProvider);
        assertEquals(3, set.getLineCount());
        for (int i = 0; i < 3; i++) {
            assertNotNull(set.getLine(i));
        }
    }

    @Test
    void getLine_returnsCorrectLine() {
        CacheSet set = new CacheSet(2, 2, mockWordProvider);
        CacheLine line0 = set.getLine(0);
        CacheLine line1 = set.getLine(1);
        assertNotNull(line0);
        assertNotNull(line1);
        assertNotSame(line0, line1);
    }

    @Test
    void findLineByTag_returnsNullIfNoValidLine() {
        CacheSet set = new CacheSet(2, 2, mockWordProvider);
        assertNull(set.findLineByTag(5));
    }

    @Test
    void findLineByTag_returnsLineWithMatchingTagAndValid() {
        CacheSet set = new CacheSet(2, 2, mockWordProvider);
        CacheLine line = set.getLine(0);
        line.setTag(42);
        line.setValid(true);

        assertSame(line, set.findLineByTag(42));
    }

    @Test
    void findLineByTag_ignoresInvalidLines() {
        CacheSet set = new CacheSet(2, 2, mockWordProvider);
        CacheLine line = set.getLine(0);
        line.setTag(99);
        line.setValid(false);

        assertNull(set.findLineByTag(99));
    }

    @Test
    void findEmptyLine_returnsFirstInvalidLine() {
        CacheSet set = new CacheSet(2, 2, mockWordProvider);
        CacheLine emptyLine = set.findEmptyLine();
        assertNotNull(emptyLine);
        assertFalse(emptyLine.isValid());

        // 1. line als valid
        set.getLine(0).setValid(true);
        CacheLine stillEmpty = set.findEmptyLine();
        assertSame(set.getLine(1), stillEmpty);
    }

    @Test
    void findEmptyLine_returnsNullIfAllValid() {
        CacheSet set = new CacheSet(2, 2, mockWordProvider);
        set.getLine(0).setValid(true);
        set.getLine(1).setValid(true);
        assertNull(set.findEmptyLine());
    }

    @Test
    void getLineCount_returnsAssociativity() {
        CacheSet set = new CacheSet(4, 2, mockWordProvider);
        assertEquals(4, set.getLineCount());
    }
}
