package com.github.mikee2509.search;

import com.github.mikee2509.search.domain.DocumentResult;

import java.util.*;

public class Index {
    private Map<String, List<DocumentResult>> index = new HashMap<>();
    private int numIndexedDocuments = 0;

    public void indexDocument(String doc) {
        int currentDoc = ++numIndexedDocuments;
        StringBuilder word = new StringBuilder();
        Integer wordPosition = null;

        for (int i = 0; i < doc.length(); i++) {
            char ch = doc.charAt(i);
            if (Character.isWhitespace(ch)) {
                if (word.length() == 0) {
                    // skip leading and trailing whitespace
                    continue;
                }
                // reached the end of a token
                String token = word.toString();
                List<DocumentResult> results = index.computeIfAbsent(token, key -> new ArrayList<>());
                Optional<DocumentResult> optionalResult = results.stream()
                    .filter(result -> result.getDocNumber() == currentDoc)
                    .findFirst();

                if (optionalResult.isPresent()) {
                    optionalResult.get().addPosition(wordPosition);
                } else {
                    results.add(new DocumentResult(currentDoc, wordPosition));
                }

                word.setLength(0);
                wordPosition = null;

            } else {
                // in the middle of a token
                if (wordPosition == null) {
                    wordPosition = i;
                }
                word.append(Character.toLowerCase(ch));
            }
        }
    }

    public List<DocumentResult> getResults(String key) {
        return index.get(key.toLowerCase());
    }
}
