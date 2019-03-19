package com.github.mikee2509.search;

import com.github.mikee2509.search.domain.DocumentResult;
import com.github.mikee2509.search.domain.SearchResult;

import java.util.List;

public class Engine {
    private Index index;

    public Engine(Index index) {
        this.index = index;
    }

    public SearchResult search(String query) {
        List<DocumentResult> results = index.getResults(query);

        // TODO

        return null;
    }
}
