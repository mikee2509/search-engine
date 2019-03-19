package com.github.mikee2509.search;

import com.github.mikee2509.search.domain.DocumentResult;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class EngineTest {
    private Index index;

    @Before
    public void setUp() {
        index = new Index();

        index.indexDocument("the brown fox jumped over the brown dog");
        index.indexDocument("the lazy brown dog sat in the corner");
        index.indexDocument("the red fox bit the lazy dog");
    }

    @Test
    public void search() {
        Engine engine = new Engine(index);

        List<DocumentResult> brownResults = engine.search("brown");
        assertEquals(2, brownResults.size());
        assertEquals(1, brownResults.get(0).getDocNumber());
        assertEquals(2, brownResults.get(1).getDocNumber());

        List<DocumentResult> foxResults = engine.search("fox");
        assertEquals(2, foxResults.size());
        assertEquals(1, foxResults.get(0).getDocNumber());
        assertEquals(3, foxResults.get(1).getDocNumber());
    }
}