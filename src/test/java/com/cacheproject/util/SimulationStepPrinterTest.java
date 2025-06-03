package com.cacheproject.util;

import com.cacheproject.domain.cache.model.Address;
import com.cacheproject.domain.cache.model.CacheAccessResult;
import com.cacheproject.domain.cache.model.Word;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class SimulationStepPrinterTest {

    @Test
    void printAccessResult_printsHitWithCorrectFormat() {
        SimulationStepPrinter printer = new SimulationStepPrinter();
        Address address = new Address(42);
        CacheAccessResult result = new CacheAccessResult(true, new Word(99));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        try {
            printer.printAccessResult(address, result);
        } finally {
            System.setOut(original);
        }

        String output = out.toString();
        assertTrue(output.contains("Accessing address"));
        assertTrue(output.contains("42"));
        assertTrue(output.contains("HIT"));
    }


    @Test
    void printAccessResult_printsMissWithCorrectFormat() {
        SimulationStepPrinter printer = new SimulationStepPrinter();
        Address address = new Address(13);
        CacheAccessResult result = new CacheAccessResult(false, new Word(7));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        try {
            printer.printAccessResult(address, result);
        } finally {
            System.setOut(original);
        }

        String output = out.toString();
        assertTrue(output.contains("Accessing address"));
        assertTrue(output.contains("13"));
        assertTrue(output.contains("MISS"));
    }
}
