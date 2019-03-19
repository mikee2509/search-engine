package com.github.mikee2509.search;

import com.github.mikee2509.search.domain.DocumentResult;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Engine {
    private Index index;

    public Engine(Index index) {
        this.index = index;
    }

    public List<DocumentResult> search(String query) {
        List<DocumentResult> results = index.getResults(query);
        if (results == null) {
            return Collections.emptyList();
        }

        // N
        int numDocuments = index.getNumIndexedDocuments();
        // n_t
        int numQueryDocuments = results.size();
        // idf = log10(N/n_t)
        double idf = Math.log10((double) numDocuments / numQueryDocuments);

        results.sort(Comparator.comparingDouble((DocumentResult value) -> tfIdf(value, idf)).reversed());
        return results;
    }

    private double tfIdf(DocumentResult documentResult, double idf) {
        int rowCount = documentResult.getRowCount();
        int maxRowCount = index.getMaxRowCount(documentResult.getDocNumber());

        // tf = 0.5 + 0.5 * ( f_(t,q) / max_t(f_(t,q)) )
        double tf = 0.5 + 0.5 * ((double) rowCount / maxRowCount);
        return tf * idf;
    }
}
