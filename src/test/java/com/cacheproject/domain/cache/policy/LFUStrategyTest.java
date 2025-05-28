package com.cacheproject.domain.cache.policy;

import com.cacheproject.domain.cache.model.CacheLine;
import com.cacheproject.domain.cache.model.CacheSet;
import com.cacheproject.domain.cache.model.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LFUStrategyTest {

    private LFUStrategy lfuStrategy;
    private CacheSet set;
    private CacheLine line1;
    private CacheLine line2;
    private CacheLine line3;

    @BeforeEach
    void setUp() {
        lfuStrategy = new LFUStrategy();
        set = new CacheSet(3, 2, size -> new Word[]{
                new Word(0), new Word(1)});
        line1 = set.getLine(0);
        line2 = set.getLine(1);
        line3 = set.getLine(2);
    }

    @Test
    void selectLineForReplacing_prefersUnaccessedLines() {
        assertSame(line1, lfuStrategy.selectLineForReplacing(set));
    }

    @Test
    void updateAccessOrder_incrementsFrequency_forSingleLine() {
        lfuStrategy.updateAccessOrder(set, line1);
        lfuStrategy.updateAccessOrder(set, line1);
        lfuStrategy.updateAccessOrder(set, line1);
        // line1: 3, line2: 0, line3: 0 -> line2 ist erste mit min count
        assertSame(line2, lfuStrategy.selectLineForReplacing(set));
    }

    @Test
    void selectLineForReplacing_returnsLeastFrequentlyUsedLine_whenFrequenciesDiffer() {
        lfuStrategy.updateAccessOrder(set, line1); // 1
        lfuStrategy.updateAccessOrder(set, line2); // 1
        lfuStrategy.updateAccessOrder(set, line3); // 1
        lfuStrategy.updateAccessOrder(set, line2); // 2
        lfuStrategy.updateAccessOrder(set, line3); // 2
        lfuStrategy.updateAccessOrder(set, line3); // 3
        // line1:1, line2:2, line3:3 -> line1 ist min
        assertSame(line1, lfuStrategy.selectLineForReplacing(set));
    }

    @Test
    void selectLineForReplacing_returnsFirstLine_whenAllCountsAreEqual() {
        lfuStrategy.updateAccessOrder(set, line1);
        lfuStrategy.updateAccessOrder(set, line2);
        lfuStrategy.updateAccessOrder(set, line3);
        assertSame(line1, lfuStrategy.selectLineForReplacing(set));
    }

    @Test
    void accessCounts_areIsolatedPerCacheSet() {
        CacheSet anotherSet = new CacheSet(1, 2, size -> new Word[]{
                new Word(0), new Word(1)});
        CacheLine anotherLine = anotherSet.getLine(0);
        lfuStrategy.updateAccessOrder(set, line1);
        lfuStrategy.updateAccessOrder(anotherSet, anotherLine);
        assertSame(line1, lfuStrategy.selectLineForReplacing(set));
        assertSame(anotherLine, lfuStrategy.selectLineForReplacing(anotherSet));
    }


    @Test
    void selectLineForReplacing_returnsFirstLine_whenNoAccesses() {
        CacheSet newSet = new CacheSet(2, 2, size -> new Word[]{
                new Word(0), new Word(1)});
        assertSame(newSet.getLine(0), lfuStrategy.selectLineForReplacing(newSet));
    }

    @Test
    void updateAccessOrder_handlesMultipleLinesAndFrequencies() {
        lfuStrategy.updateAccessOrder(set, line1); // 1
        lfuStrategy.updateAccessOrder(set, line2); // 1
        lfuStrategy.updateAccessOrder(set, line2); // 2
        lfuStrategy.updateAccessOrder(set, line3); // 1
        lfuStrategy.updateAccessOrder(set, line3); // 2
        lfuStrategy.updateAccessOrder(set, line3); // 3
        // line1:1, line2:2, line3:3 -> line1 ist min
        assertSame(line1, lfuStrategy.selectLineForReplacing(set));
        lfuStrategy.updateAccessOrder(set, line1);
        // line2:2, line3:3, line1:2 -> line1 und line2 min, line1 ist erste
        assertSame(line1, lfuStrategy.selectLineForReplacing(set));
    }
}
