package com.cacheproject.domain.cache.model;

import com.cacheproject.domain.cache.model.CacheLine;

import java.util.Arrays;

public class CacheSet {
    public final CacheLine[] lines;

    public CacheSet(int associativity, int wordsPerLine) {
        this.lines = new CacheLine[associativity];
        for (int i = 0; i < associativity; i++) {
            lines[i] = new CacheLine(wordsPerLine);
        }
    }

    public CacheLine getLine(int index) {
        return lines[index];
    }

    public CacheLine findLineByTag(int tag) {
        return Arrays.stream(lines)
                .filter(CacheLine::isValid)
                .filter(line -> line.getTag() == tag)
                .findFirst()
                .orElse(null);
    }

    public CacheLine findEmptyLine(){
        return Arrays.stream(lines)
                .filter(line -> !line.isValid())
                .findFirst()
                .orElse(null);
    }

    public int getLineCount(){
        return lines.length;
    }
}
