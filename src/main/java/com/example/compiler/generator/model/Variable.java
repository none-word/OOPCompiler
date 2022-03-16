package com.example.compiler.generator.model;

import com.example.compiler.syntaxer.Node;
import lombok.Data;

@Data
public class Variable {

    private String name;
    private String typeName;
    private Node expression;
    private JvmType type;

    private void defineType() {
        switch (typeName) {
            case "Integer":
                type = JvmType.INTEGER;
                break;
            case "Real":
                type = JvmType.REAL;
                break;
            case "Boolean":
                type = JvmType.BOOLEAN;
                break;
            default:
                type = JvmType.REFERENCE;
        }

    }

    public Variable(String name, String typeName) {
        this.name = name;
        this.typeName = typeName;
        defineType();
    }

    public Variable(String name, Node expression) {
        this.name = name;
        this.expression = expression;
    }
}
