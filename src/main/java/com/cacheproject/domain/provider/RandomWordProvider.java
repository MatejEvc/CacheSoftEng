package com.cacheproject.domain.provider;

import com.cacheproject.domain.cache.model.Word;

import java.util.Random;

public class RandomWordProvider implements WordProvider {
    private final Random random = new Random();

    @Override
    public Word[] provideWords(int size) {
        Word[] words = new Word[size];
        for (int i = 0; i < size; i++) {
            words[i] = new Word(random.nextInt(1000)); // Zufällige Werte zwischen 0 und 999
        }
        return words;
    }
}
