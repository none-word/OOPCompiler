package com.example.compiler.syntaxer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Tree {
    Node root;

    @Data
    @AllArgsConstructor
    @RequiredArgsConstructor
    private class Node {
        private FormalGrammar value;
        private ArrayList<Node> childNode;
    }
}
