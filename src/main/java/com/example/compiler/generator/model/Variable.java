package com.example.compiler.generator.model;

import lombok.Data;

@Data
public class Variable {

    private String name;
    private String type;

    public Variable(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
