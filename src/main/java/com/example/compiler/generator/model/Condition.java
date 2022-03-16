package com.example.compiler.generator.model;

import com.example.compiler.syntaxer.Node;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Condition {

    private Node expression;
    private Node trueBody;
    private Node elseBody;

}
