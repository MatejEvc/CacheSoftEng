package com.cacheproject.domain.provider;

import com.cacheproject.domain.cache.model.Word;

public interface WordProvider {
    /**
     * Liefert ein Array von Words für eine CacheLine.
     *
     * @param size Die Anzahl der Worte, die benötigt werden.
     * @return Ein Array von Wörtern.
     */
    Word[] provideWords(int size);
}
