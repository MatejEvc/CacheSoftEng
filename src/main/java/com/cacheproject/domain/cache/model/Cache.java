package com.cacheproject.domain.cache.model;

import com.cacheproject.domain.cache.policy.ReplacementStrategy;
import com.cacheproject.domain.provider.WordProvider;

public class Cache {
    private final CacheSet[] sets;
    private final int setIndexBits;
    private final int offsetBits;
    private final ReplacementStrategy replacementStrategy;
    private final WordProvider wordProvider;
    private final CacheStatistics statistics = new CacheStatistics();

    public Cache(int setCount, int associativity, int wordsPerLine, ReplacementStrategy
            replacementStrategy, WordProvider wordProvider) {
        this.sets = new CacheSet[setCount];
        for (int i = 0; i < setCount; i++) {
            sets[i] = new CacheSet(associativity, wordsPerLine, wordProvider);
        }
        this.offsetBits = Integer.numberOfTrailingZeros(wordsPerLine);
        this.setIndexBits = Integer.numberOfTrailingZeros(setCount);
        this.replacementStrategy = replacementStrategy;
        this.wordProvider = wordProvider;
    }

    public CacheAccessResult access(Address address) {
        int setIndex = address.getSetIndex(setIndexBits, offsetBits);
        int tag = address.getTag(setIndexBits, offsetBits);
        int offset = address.getOffset(offsetBits);

        System.out.printf("Adresse %d (%8s) → Set=%d (Binär: %3s), Tag=%d (Binär: %3s)%n",
                address.getValue(),
                String.format("%8s", Integer.toBinaryString(address.getValue())).replace(' ', '0'),
                setIndex,
                Integer.toBinaryString(setIndex),
                tag,
                Integer.toBinaryString(tag));

        CacheSet set = sets[setIndex];
        CacheLine line = set.findLineByTag(tag);

        if (line != null && line.isValid()) {
            replacementStrategy.updateAccessOrder(set, line);
            statistics.recordAccess(true);
            return new CacheAccessResult(true, line.getWord(offset));

        } else {
            CacheLine newLine = set.findEmptyLine();
            boolean evicted = false;
            if (newLine == null) {
                newLine = replacementStrategy.selectLineForReplacing(set);
                evicted = true;
            }
            newLine.setValid(true);
            newLine.setTag(tag);

            replacementStrategy.updateAccessOrder(set, newLine);
            statistics.recordAccess(false);
            if(evicted) {
                statistics.recordEviction();
            }
            return new CacheAccessResult(false, newLine.getWord(offset));
        }
    }

    public int getSetCount() {
        return sets.length;
    }

    public CacheSet getSet(int index) {
        return sets[index];
    }

    public WordProvider getWordProvider() {
        return wordProvider;
    }

    public CacheStatistics getStatistics() {
        return statistics;
    }

}
