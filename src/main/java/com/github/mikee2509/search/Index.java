package com.github.mikee2509.search;

import com.github.mikee2509.search.domain.DocumentResult;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Index {
    // mapping of tokens to documents in which they appear
    private Map<String, List<DocumentResult>> index = new HashMap<>();

    // mapping of indexed documents (array index) to the row count of most occurring token
    private List<Integer> maxRowCounts = new ArrayList<>();

    public void indexDocument(String doc) {
        int currentDoc = maxRowCounts.size() + 1;
        HashMap<String, Integer> wordFrequencyMap = new HashMap<>();

        StringBuilder word = new StringBuilder();
        Integer wordPosition = null;

        for (int i = 0; i < doc.length(); i++) {
            char ch = doc.charAt(i);
            boolean lastChar = i == (doc.length() - 1);

            if (Character.isWhitespace(ch) || lastChar) {
                if (Character.isWhitespace(ch) && word.length() == 0) {
                    // skip leading and trailing whitespace
                    continue;
                }
                if (lastChar && !Character.isWhitespace(ch)) {
                    // append the last char
                    wordPosition = i;
                    word.append(Character.toLowerCase(ch));
                }
                // reached the end of a token
                String token = word.toString();
                wordFrequencyMap.compute(token, (k, currFreq) -> currFreq == null ? 1 : currFreq + 1);

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

        Integer maxRowCount = wordFrequencyMap.values().stream().max(Comparator.naturalOrder()).orElse(0);
        maxRowCounts.add(maxRowCount);
    }

    public List<DocumentResult> getResults(String key) {
        return index.get(key.toLowerCase());
    }

    public int getNumIndexedDocuments() {
        return maxRowCounts.size();
    }

    public int getMaxRowCount(int docNumber) {
        return maxRowCounts.get(docNumber - 1);
    }

    @Override
    public String toString() {
        Integer maxTermLen = index.keySet().stream()
            .map(String::length)
            .max(Comparator.naturalOrder())
            .orElse(0);

        final String firstLine = "Term" + getWhiteSpace(4, maxTermLen) + "| Documents\n";
        final String secondLine = IntStream.range(1, firstLine.length())
            .boxed()
            .map(integer -> "-")
            .collect(Collectors.joining()) + "\n";

        return firstLine + secondLine +
            index.entrySet().stream()
                .map(entry -> {
                    String collect = entry.getValue().stream()
                        .map(DocumentResult::getDocNumber)
                        .map(Objects::toString)
                        .collect(Collectors.joining(" "));
                    return entry.getKey() + getWhiteSpace(entry.getKey().length(), maxTermLen) + "| " + collect;
                })
                .collect(Collectors.joining("\n"));
    }

    private String getWhiteSpace(int termLen, int maxTermLen) {
        return IntStream.rangeClosed(termLen, maxTermLen)
            .boxed()
            .map(integer -> " ")
            .collect(Collectors.joining());
    }
}
