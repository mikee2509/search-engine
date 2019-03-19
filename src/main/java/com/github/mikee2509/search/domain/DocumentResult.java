package com.github.mikee2509.search.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DocumentResult {
    private int docNumber;
    private List<Integer> queryPositions;

    public DocumentResult(int docNumber, int firstPosition) {
        this.docNumber = docNumber;
        this.queryPositions = new ArrayList<>(Collections.singletonList(firstPosition));
    }

    public int getDocNumber() {
        return docNumber;
    }

    public void addPosition(int position) {
        queryPositions.add(position);
    }
}
