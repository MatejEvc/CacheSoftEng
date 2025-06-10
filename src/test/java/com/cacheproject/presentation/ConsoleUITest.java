package com.cacheproject.presentation;

import com.cacheproject.domain.cache.model.Cache;
import com.cacheproject.domain.service.CacheService;
import com.cacheproject.util.CacheStateRenderer;
import com.cacheproject.util.SimulationStepPrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConsoleUITest {

    private CacheService mockService;
    private CacheStateRenderer mockRenderer;
    private SimulationStepPrinter mockPrinter;
    private ConsoleUI ui;

    @BeforeEach
    void setUp() {
        mockService = mock(CacheService.class);
        mockRenderer = mock(CacheStateRenderer.class);
        mockPrinter = mock(SimulationStepPrinter.class);
        ui = new ConsoleUI(mockService, "testCache");
    }

    @Test
    void displayCacheState_printsCacheState() {
        Cache mockCache = mock(Cache.class);
        when(mockService.getCache("testCache")).thenReturn(mockCache);
        when(mockRenderer.renderAsAscii(mockCache)).thenReturn("CACHE_STATE_OUTPUT");

        PrintStream originalOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        try {
            ui.displayCacheState();
        } finally {
            System.setOut(originalOut);
        }

        String output = out.toString();
        assertTrue(output.contains("Cache State (Binary Representation):"));
    }

}
