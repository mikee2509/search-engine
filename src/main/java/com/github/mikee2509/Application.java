package com.github.mikee2509;

import com.github.mikee2509.search.Engine;
import com.github.mikee2509.search.Index;
import com.github.mikee2509.search.domain.DocumentResult;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please provide documents file path as an argument");
            return;
        }

        Index index = new Index();
        Engine engine = new Engine(index);

        FileReader fileReader;
        try {
            fileReader = new FileReader(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println("Failed to open given file");
            return;
        }

        new BufferedReader(fileReader).lines().forEach(index::indexDocument);

        System.out.println("BUILT INDEX");
        System.out.println(index + "\n");

        Scanner in = new Scanner(System.in);
        try {
            while (true) {
                System.out.print("Enter search query: ");
                String query = in.nextLine();
                final List<DocumentResult> results = engine.search(query);
                if (results.isEmpty()) {
                    System.out.println("Not found\n");
                } else {
                    final String docs = results.stream()
                        .map(res -> "document " + res.getDocNumber())
                        .collect(Collectors.joining(", "));
                    System.out.print("Result: [" + docs + "]\n\n");
                }
            }
        } catch (Exception ignored) {
        }
    }
}
