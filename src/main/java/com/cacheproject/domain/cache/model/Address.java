package com.cacheproject.domain.cache.model;

public class Address {
    private final int value;

    public Address(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int getTag(int setIndexBits, int offsetBits) {
        //Fully Associative  Tag = address /blockSize
        if (setIndexBits == 0) {
            return value >>> (offsetBits);
        }

        return value >>> (setIndexBits + offsetBits);
    }

    public int getSetIndex(int setIndexBits, int offsetBits) {
        // für fully Associative: 0 SetIndex
        if (setIndexBits == 0) return 0;

        return (value >>> offsetBits) & ((1 << setIndexBits) - 1);
    }


    public String getBitAnalysis(int offsetBits, int setIndexBits) {
        String binary = String.format("%32s", Integer.toBinaryString(value)).replace(' ', '0');
        binary = binary.substring(binary.length() - 8); // Nur die rechten 8 Bits

        int offsetMask = (1 << offsetBits) - 1;
        int setMask = (1 << setIndexBits) - 1;

        int offset = value & offsetMask;
        int set = (value >>> offsetBits) & setMask;
        int tag = value >>> (offsetBits + setIndexBits);

        return String.format(
                "Adresse %3d (%8s): Offset=%2s | Set=%3s | Tag=%3s%n",
                value,
                binary,
                binary.substring(binary.length() - offsetBits),
                binary.substring(binary.length() - offsetBits - setIndexBits, binary.length() - offsetBits),
                binary.substring(0, binary.length() - offsetBits - setIndexBits)
        );
    }

    public int getOffset(int offsetBits) {
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
