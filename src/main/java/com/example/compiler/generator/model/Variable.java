package com.example.compiler.generator.model;

import com.example.compiler.syntaxer.Node;
import lombok.Data;

@Data
public class Variable {

    private String name;
    private String typeName;
    private Node expression;
    private JvmType type;
    private String typeChar;

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

    private String computeDescriptor(JvmType type) {
        switch (type) {
            case REAL:
                return "D";
            case BOOLEAN:
                return "B";
            case INTEGER:
                return "I";
            default:
                return "A";
        }
    }

    public Variable(String name, String typeName) {
        this.name = name;
        this.typeName = typeName;
        defineType();
        this.typeChar = computeDescriptor(this.type);
    }

    public Variable(String name, Node expression) {
        this.name = name;
        this.expression = expression;
    }
}
