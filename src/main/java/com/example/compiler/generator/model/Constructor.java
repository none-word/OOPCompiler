package com.example.compiler.generator.model;

import java.util.List;
import lombok.Data;

@Data
public class Constructor {

    private List<Variable> parameters;
    private List<Variable> variables;
    private String className = "";


    public Constructor(List<Variable> parameters, List<Variable> variables) {
        this.parameters = parameters;
        this.variables = variables;
    }

}
