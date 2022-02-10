package com.example.compiler.syntaxer;

import lombok.Data;

@Data
public class Tree {
    private final Node root;

    public Tree() {
        root = new Node(FormalGrammar.PROGRAM);
    }

    public Node addNode(FormalGrammar formalGrammar, Node parentNode) {
        return parentNode.addChild(new Node(formalGrammar));
    }

    public void addNode(FormalGrammar formalGrammar, String value, Node parentNode) {
        parentNode.addChild(new Node(formalGrammar, value));
    }
}
