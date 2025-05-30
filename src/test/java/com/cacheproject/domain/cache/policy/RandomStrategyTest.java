package com.cacheproject.domain.cache.policy;

import com.cacheproject.domain.cache.model.CacheLine;
import com.cacheproject.domain.cache.model.CacheSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RandomStrategyTest {

    private RandomStrategy randomStrategy;
    private CacheSet set;
    private CacheLine line1;
    private CacheLine line2;
    private CacheLine line3;

    @BeforeEach
    void setUp() {
        // funktioniert nicht https://medium.com/@abi12subramaniam/python-random-seed-42-125a3f2e068f :)
        randomStrategy = new RandomStrategy(new Random(42));
        set = mock(CacheSet.class);
        line1 = mock(CacheLine.class);
        line2 = mock(CacheLine.class);
        line3 = mock(CacheLine.class);

        when(set.getLineCount()).thenReturn(3);
        when(set.getLine(0)).thenReturn(line1);
        when(set.getLine(1)).thenReturn(line2);
        when(set.getLine(2)).thenReturn(line3);
    }


    @Test
    void selectLineForReplacing_returnsOnlyLine_whenSingleLine() {
        CacheSet singleSet = mock(CacheSet.class);
        CacheLine onlyLine = mock(CacheLine.class);
        when(singleSet.getLineCount()).thenReturn(1);
        when(singleSet.getLine(0)).thenReturn(onlyLine);

        CacheLine selected = randomStrategy.selectLineForReplacing(singleSet);
        assertSame(onlyLine, selected);
    }

    @Test
    void updateAccessOrder_doesNotThrow_andHasNoEffect() {
        // RandomStrategy ignoriert updateAccessOrder
        assertDoesNotThrow(() -> randomStrategy.updateAccessOrder(set, line1));
    }
}
