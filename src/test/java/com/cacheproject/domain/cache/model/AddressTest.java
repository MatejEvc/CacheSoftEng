package com.cacheproject.domain.cache.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void getValue_returnsCorrectValue() {
        Address address = new Address(42);
        assertEquals(42, address.getValue());
    }

    @Test
    void getTag_directMappedAndNWay() {
        // Adresse 0b110010 (50), setIndexBits = 2, offsetBits = 2
        Address address = new Address(50); // 0b110010
        int tag = address.getTag(2, 2);
        //50 >> (2+2) ->50 >> 4 = 3
        assertEquals(3, tag);
    }

    @Test
    void getTag_fullyAssociative() {
        Address address = new Address(0b1011011); // 91
        int tag = address.getTag(0, 3);
        assertEquals(11, tag);
    }

    @Test
    void getSetIndex_directMappedAndNWay() {
        Address address = new Address(73);
        int setIndex = address.getSetIndex(3, 2);
        assertEquals(2, setIndex);
    }

    @Test
    void getSetIndex_fullyAssociativeAlwaysZero() {
        Address address = new Address(123);
        assertEquals(0, address.getSetIndex(0, 2));
    }

    @Test
    void getOffset_returnsCorrectBits() {
        Address address = new Address(77);
        assertEquals(5, address.getOffset(3));
    }

    @Test
    void getBitAnalysis_returnsExpectedString() {
        Address address = new Address(255); // 0b11111111
        String analysis = address.getBitAnalysis(2, 3);
        assertTrue(analysis.contains("Offset="));
        assertTrue(analysis.contains("Set="));
        assertTrue(analysis.contains("Tag="));
        assertTrue(analysis.contains("255"));
    }

    @Test
    void equalsAndHashCode_contract() {
        Address a1 = new Address(42);
        Address a2 = new Address(42);
        Address a3 = new Address(43);

        assertEquals(a1, a2);
        assertEquals(a1.hashCode(), a2.hashCode());
        assertNotEquals(a1, a3);
    }

    @Test
    void toString_containsValue() {
        Address address = new Address(99);
        assertTrue(address.toString().contains("99"));
    }
}
