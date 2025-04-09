package com.cacheproject.config;

import com.cacheproject.domain.cache.policy.ReplacementStrategy;
import com.cacheproject.domain.provider.WordProvider;

public class CacheConfiguration {
    private final int setCount;
    private final int associativity;
    private final int wordsPerLine;
    private final ReplacementStrategy replacementStrategy;
    private final WordProvider wordProvider;

    public CacheConfiguration(int setCount, int associativity, int wordsPerLine, ReplacementStrategy replacementStrategy, WordProvider wordProvider) {
        this.setCount = setCount;
        this.associativity = associativity;
        this.wordsPerLine = wordsPerLine;
        this.replacementStrategy = replacementStrategy;
        this.wordProvider = wordProvider;
    }

    public int getSetCount() {
        return setCount;
    }

    public int getAssociativity() {
        return associativity;
    }

    public int getWordsPerLine() {
        return wordsPerLine;
    }

    public ReplacementStrategy getReplacementStrategy() {
        return replacementStrategy;
    }

    public WordProvider getWordProvider() {
        return wordProvider;
    }
}
