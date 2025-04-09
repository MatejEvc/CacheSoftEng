package com.cacheproject.domain.provider;

import com.cacheproject.domain.cache.model.Word;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileWordProvider implements WordProvider {
    private final String filePath;

    public FileWordProvider(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Word[] provideWords(int size) {
        Word[] words = new Word[size];
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            for (int i = 0; i < size; i++) {
                String line = reader.readLine();
                if (line == null) break;
                words[i] = new Word(Integer.parseInt(line.trim())); 
            }
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Fehler beim Lesen der Datei: " + filePath, e);
        }
        return words;
    }
}
