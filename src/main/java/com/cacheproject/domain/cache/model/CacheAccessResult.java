package com.cacheproject.domain.cache.model;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheAccessResult that = (CacheAccessResult) o;
        return hit == that.hit &&
                (word != null ? word.equals(that.word) : that.word == null);
    }

    @Override
    public int hashCode() {
        int result = Boolean.hashCode(hit);
        result = 31 * result + (word != null ? word.hashCode() : 0);
        return result;
    }

}
