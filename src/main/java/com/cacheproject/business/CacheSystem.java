package com.cacheproject.business;

public class CacheSystem {
    private final CacheSet[] sets;
    private final int setIndexBits;
    private final int offsetBits;
    private final ReplacementStrategy replacementStrategy;

    public CacheSystem(int setCount, int associativity, int wordsPerLine, ReplacementStrategy
                       replacementStrategy){
        this.sets = new CacheSet[setCount];
        for(int i = 0; i < setCount; i++){
            sets[i] = new CacheSet(associativity, wordsPerLine);
        }
        this.offsetBits = Integer.numberOfTrailingZeros(wordsPerLine);
        this.setIndexBits = Integer.numberOfTrailingZeros(setCount);
        this.replacementStrategy = replacementStrategy;
    }

    public CacheAccessResult access(Address address){
        int setIndex = address.getSetIndex(setIndexBits, offsetBits);
        int tag = address.getTag(setIndexBits, offsetBits);
        int offset = address.getOffset(offsetBits);

        System.out.printf("DEBUG: Adresse %d (%8s) → Set=%d (Binär: %3s), Tag=%d (Binär: %3s)%n",
                address.getValue(),
                String.format("%8s", Integer.toBinaryString(address.getValue())).replace(' ', '0'),
                setIndex,
                Integer.toBinaryString(setIndex),
                tag,
                Integer.toBinaryString(tag));

        CacheSet set = sets[setIndex];
        CacheLine line = set.findLineByTag(tag);

        if(line != null && line.isValid()){
            replacementStrategy.updateAccessOrder(set, line);
//            System.out.printf("Adresse %d → Set %d, Tag %d%n", address.getValue(), setIndex, tag); //DEBUG
            return new CacheAccessResult(true, line.getWord(offset));

        } else {
            CacheLine newLine = set.findEmptyLine();
            if(newLine == null) {
                newLine = replacementStrategy.selectLineForReplacing(set);
            }
            newLine.setValid(true);
            newLine.setTag(tag);
            //hier data fetchen
            for(int i = 0; i < newLine.getData().length; i++){
                newLine.setWord(i, new Word(0)); //Platzhalter
            }
            replacementStrategy.updateAccessOrder(set, newLine);
//            System.out.printf("Adresse %d → Set %d, Tag %d%n" address.getValue(), setIndex, tag); //DEBUG
            return new CacheAccessResult(false, newLine.getWord(offset));
        }
    }

    public int getSetCount(){
        return sets.length;
    }

    public CacheSet getSet(int index){
        return sets[index];
    }

}
