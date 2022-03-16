package com.example.compiler.generator.model;

import com.example.compiler.syntaxer.Node;
import lombok.Data;

@Data
public class Variable {

    private String name;
    private Node expression;

    public Variable(String name, Node expression) {
        this.name = name;
        this.expression = expression;
    }
}
