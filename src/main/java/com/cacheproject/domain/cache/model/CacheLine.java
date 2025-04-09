package com.cacheproject.domain.cache.model;

import com.cacheproject.domain.provider.WordProvider;

public class CacheLine {
    private boolean valid;
    private int tag;
    private Word[] data;

    public CacheLine(int wordsPerLine, WordProvider wordProvider) {
        this.valid = false;
        this.tag = -1;
        this.data = wordProvider.provideWords(wordsPerLine);
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid){
        this.valid = valid;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public Word[] getData() {
        return data;
    }

    public void setData(Word[] data) {
        this.data = data;
    }

    public Word getWord(int offset){
        return data[offset];
    }

    public void setWord(int offset, Word word){
        data[offset] = word;
    }
}
