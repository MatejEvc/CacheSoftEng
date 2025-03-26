package com.cacheproject.business;

import java.util.Objects;

public class Address {
    private final int value;

    public Address(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public int getTag(int setIndexBits, int offsetBits){
        return value >>> (setIndexBits + offsetBits);
    }

    public int getSetIndex(int setIndexBits, int offsetBits) {
        // Erst Offset entfernen, dann nur die Set-Bits maskieren
        return (value >> offsetBits) & ((1 << setIndexBits) - 1);
    }

    public void printBitAnalysis(int offsetBits, int setIndexBits) {
        String binary = String.format("%32s", Integer.toBinaryString(value)).replace(' ', '0');
        binary = binary.substring(binary.length() - 8); // Nur die rechten 8 Bits

        int offsetMask = (1 << offsetBits) - 1;
        int setMask = (1 << setIndexBits) - 1;

        int offset = value & offsetMask;
        int set = (value >>> offsetBits) & setMask;
        int tag = value >>> (offsetBits + setIndexBits);

        System.out.printf(
                "Adresse %3d (%8s): Offset=%2s | Set=%3s | Tag=%3s%n",
                value,
                binary,
                binary.substring(binary.length() - offsetBits),
                binary.substring(binary.length() - offsetBits - setIndexBits, binary.length() - offsetBits),
                binary.substring(0, binary.length() - offsetBits - setIndexBits)
        );
    }

    public void debugBitExtraction(int offsetBits, int setIndexBits) {
        int shifted = value >> offsetBits;
        int mask = (1 << setIndexBits) - 1;
        int setIndex = shifted & mask;

        System.out.printf(
                "DEBUG: value=%d (%8s) >> %d = %d (%s) & %d (%s) → SetIndex=%d (%s)%n",
                value,
                String.format("%8s", Integer.toBinaryString(value)).replace(' ', '0'),
                offsetBits,
                shifted,
                Integer.toBinaryString(shifted),
                mask,
                Integer.toBinaryString(mask),
                setIndex,
                Integer.toBinaryString(setIndex)
        );
    }

    public int getOffset(int offsetBits){
        return value & ((1 << offsetBits) - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return value == address.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    @Override
    public String toString() {
        return "Address{" +
                "value=" + value +
                '}';
    }
}
