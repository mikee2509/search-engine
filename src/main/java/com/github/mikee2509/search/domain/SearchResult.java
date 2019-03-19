package com.github.mikee2509.search.domain;

public class SearchResult {
    private int docNumber;
    private int position;

    public SearchResult(int docNumber, int position) {
        this.docNumber = docNumber;
        this.position = position;
    }

    public int getDocNumber() {
        return docNumber;
    }

    public int getPosition() {
        return position;
    }
}
