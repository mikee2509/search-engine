package com.github.mikee2509.search;

import com.github.mikee2509.search.domain.DocumentResult;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class IndexTest {
    @Test
    public void indexDocument() {
        Index index = new Index();

        index.indexDocument("the brown fox jumped over the brown dog");
        index.indexDocument("the lazy brown dog sat in the corner");
        index.indexDocument("the red fox bit the lazy dog");

        assertEquals(3, index.getNumIndexedDocuments());
        assertEquals(2, index.getMaxRowCount(1));
        assertEquals(2, index.getMaxRowCount(2));
        assertEquals(2, index.getMaxRowCount(3));

        List<DocumentResult> theResults = index.getResults("the");
        assertEquals(3, theResults.size());
        assertEquals(1, theResults.get(0).getDocNumber());
        assertEquals(2, theResults.get(1).getDocNumber());
        assertEquals(3, theResults.get(2).getDocNumber());

        assertEquals(2, theResults.get(0).getRowCount());
        assertEquals(2, theResults.get(1).getRowCount());
        assertEquals(2, theResults.get(2).getRowCount());

        List<DocumentResult> foxResults = index.getResults("fox");
        assertEquals(2, foxResults.size());
        assertEquals(1, foxResults.get(0).getDocNumber());
        assertEquals(3, foxResults.get(1).getDocNumber());
        assertEquals(10, foxResults.get(0).getQueryPositions().get(0).intValue());
        assertEquals(8, foxResults.get(1).getQueryPositions().get(0).intValue());

        List<DocumentResult> catResults = index.getResults("cat");
        assertNull(catResults);
    }
}