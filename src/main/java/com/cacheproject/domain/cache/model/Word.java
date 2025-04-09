package com.cacheproject.domain.cache.model;

public class Word {
    private final int value;

    public Word(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return value == word.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    @Override
    public String toString() {
        return "Word{" +
                "value=" + value +
                '}';
    }
}
