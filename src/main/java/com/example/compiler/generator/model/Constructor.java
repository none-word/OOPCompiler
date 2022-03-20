package com.example.compiler.generator.model;

import com.example.compiler.syntaxer.Node;
import java.util.List;
import lombok.Data;

@Data
public class Constructor {

    private List<Variable> parameters;
    private List<Variable> variables;
    private String className = "";
    private Node body;


    public Constructor(List<Variable> parameters, List<Variable> variables, Node body) {
        this.parameters = parameters;
        this.variables = variables;
        this.body = body;
    }

}
