package com.example.compiler.generator.model;

import com.example.compiler.syntaxer.Node;
import lombok.Data;

@Data
public class Variable {

    private String name;
    private String typeName;
    private Node expression;
    private JvmType type;

//    private void defineType() {
//        case typeName:
//
//    }

    public Variable(String name, String typeName) {
        this.name = name;
        this.typeName = typeName;

    }

    public Variable(String name, Node expression) {
        this.name = name;
        this.expression = expression;
    }
}
