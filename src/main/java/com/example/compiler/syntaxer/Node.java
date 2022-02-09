package com.example.compiler.syntaxer;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Node {
    private FormalGrammar type;
    private String value;
    private List<Node> childNodes;

    public Node(FormalGrammar formalGrammar) {
        childNodes = new ArrayList<>();
        type = formalGrammar;
    }

    public Node(FormalGrammar formalGrammar, String value) {
        childNodes = new ArrayList<>();
        type = formalGrammar;
        this.value = value;
    }

    public Node addChild(Node childNode) {
        childNodes.add(childNode);
        return childNode;
    }
}
