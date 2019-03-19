package com.github.mikee2509;

import com.github.mikee2509.search.Engine;
import com.github.mikee2509.search.Index;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Application {

    public static void main(String[] args) {
        System.out.println("Hello world!");

        Index index = new Index();
        Engine engine = new Engine(index);

        new BufferedReader(new InputStreamReader(System.in))
            .lines()
            .forEach(index::indexDocument);

    }
}
