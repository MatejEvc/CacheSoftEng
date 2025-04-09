package com.cacheproject.util;

import com.cacheproject.domain.cache.model.CacheLine;
import com.cacheproject.domain.cache.model.CacheSet;
import com.cacheproject.domain.cache.model.Cache;

public class CacheStateRenderer {
public String renderAsAscii(Cache cache) {
    StringBuilder sb = new StringBuilder();
    sb.append("Cache State (Binary Representation):\n");
    sb.append("+---------+---------+---------+-----------------+\n");
    sb.append("| Set     | Line    | Valid   | Tag (bin)       |\n");
    sb.append("+---------+---------+---------+-----------------+\n");

    for (int i = 0; i < cache.getSetCount(); i++) {
        CacheSet set = cache.getSet(i);
        for (int j = 0; j < set.getLineCount(); j++) {
            CacheLine line = set.getLine(j);
            String tagBinary = line.isValid()
                    ? String.format("%3s", Integer.toBinaryString(line.getTag())).replace(' ', '0')
                    : "-----";
            sb.append(String.format("| %3s (%2d) | %4s (%1d) | %5b | %15s |\n",
                    Integer.toBinaryString(i), i,
                    Integer.toBinaryString(j), j,
                    line.isValid(),
                    tagBinary));
        }
    }
    sb.append("+---------+---------+---------+-----------------+\n");
    return sb.toString();
}

    private String formatData(CacheLine line) {
        if(!line.isValid()){
            return "Empty";
        }
        return String.format("%d words", line.getData().length);
    }
}
