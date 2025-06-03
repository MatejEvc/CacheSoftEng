package com.cacheproject.domain.provider;

import com.cacheproject.domain.cache.model.Word;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileWordProviderTest {

    @Test
    void provideWords_readsCorrectValuesFromFile() throws IOException {
        File tempFile = File.createTempFile("words", ".txt");
        tempFile.deleteOnExit();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("10\n");
            writer.write("20\n");
            writer.write("30\n");
        }

        FileWordProvider provider = new FileWordProvider(tempFile.getAbsolutePath());
        Word[] words = provider.provideWords(3);

        assertNotNull(words);
        assertEquals(3, words.length);
        assertEquals(new Word(10), words[0]);
        assertEquals(new Word(20), words[1]);
        assertEquals(new Word(30), words[2]);
    }

    @Test
    void provideWords_returnsArrayWithNulls_whenFileHasTooFewLines() throws IOException {
        File tempFile = File.createTempFile("words", ".txt");
        tempFile.deleteOnExit();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("7\n");
        }

        FileWordProvider provider = new FileWordProvider(tempFile.getAbsolutePath());
        Word[] words = provider.provideWords(3);

        assertNotNull(words);
        assertEquals(3, words.length);
        assertEquals(new Word(7), words[0]);
        assertNull(words[1]);
        assertNull(words[2]);
    }

    @Test
    void provideWords_throwsRuntimeException_onInvalidFile() {
        FileWordProvider provider = new FileWordProvider("notexisting_file.txt");
        assertThrows(RuntimeException.class, () -> provider.provideWords(2));
    }

    @Test
    void provideWords_throwsRuntimeException_onInvalidNumberFormat() throws IOException {
        File tempFile = File.createTempFile("words", ".txt");
        tempFile.deleteOnExit();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("abc\n");
        }

        FileWordProvider provider = new FileWordProvider(tempFile.getAbsolutePath());
        assertThrows(RuntimeException.class, () -> provider.provideWords(1));
    }
}
