package com.cacheproject.util;

import com.cacheproject.business.Address;
import com.cacheproject.business.CacheAccessResult;

public class SimulationStepPrinter {
    public void printAccessResult(Address address, CacheAccessResult result) {
        int addr = address.getValue();
        System.out.printf(
                "Accessing address %3d (%8s): %4s → Set=%3s (%2d), Tag=%3s (%2d), Offset=%2s (%1d)%n",
                addr,
                String.format("%8s", Integer.toBinaryString(addr)).replace(' ', '0'),
                result.isHit() ? "HIT" : "MISS",
                Integer.toBinaryString(addr >>> 2 & 0b111), // Set (3 Bits)
                addr >>> 2 & 0b111,
                Integer.toBinaryString(addr >>> 5),         // Tag (Rest)
                addr >>> 5,
                Integer.toBinaryString(addr & 0b11),        // Offset (2 Bits)
                addr & 0b11
        );
    }
}
