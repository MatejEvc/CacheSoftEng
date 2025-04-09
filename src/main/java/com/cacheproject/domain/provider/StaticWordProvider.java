package com.cacheproject.domain.provider;

import com.cacheproject.domain.cache.model.Word;

public class StaticWordProvider implements WordProvider {
    private final int staticValue;

    public StaticWordProvider(int staticValue) {
        this.staticValue = staticValue;
    }

    @Override
    public Word[] provideWords(int size) {
        Word[] words = new Word[size];
        for (int i = 0; i < size; i++) {
            words[i] = new Word(staticValue); //alle Worte gleich
        }
        return words;
    }
}
