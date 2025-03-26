package com.cacheproject.business;

public class CacheAccessResult {
    private final boolean hit;
    private final Word word;

    public CacheAccessResult(boolean hit, Word word) {
        this.hit = hit;
        this.word = word;
    }

    public boolean isHit() {
        return hit;
    }

    public Word getWord() {
        return word;
    }

    @Override
    public String   toString() {
        return "CacheAccessResult{" +
                "hit=" + hit +
                ", word=" + word +
                '}';
    }
}
