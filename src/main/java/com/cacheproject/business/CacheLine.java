package com.cacheproject.business;

public class CacheLine {
    private boolean valid;
    private int tag;
    private Word[] data;

    public CacheLine(int wordsPerLine){
        this.valid = false;
        this.tag = -1;
        this.data = new Word[wordsPerLine];
        for(int i = 0; i < wordsPerLine; i++){
            this.data[i] = new Word(0);  //Default Words, evtl. aus einer Textdatei random lesen
        }
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
