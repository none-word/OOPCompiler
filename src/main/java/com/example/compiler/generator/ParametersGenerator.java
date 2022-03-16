package com.example.compiler.generator;

import com.example.compiler.generator.model.Variable;
import com.example.compiler.syntaxer.Node;
import com.example.compiler.syntaxer.TreeUtil;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class ParametersGenerator {

    public String generateParameters(Node node, String returnType) {
        return "(" + TreeUtil.getParameters(node).stream()
                .map(Variable::getTypeChar)
                .collect(Collectors.joining()) + ")" + defineType(returnType);
    }

    private String defineType(String typeName) {
        switch (typeName) {
            case "Integer":
                return "I";
            case "Real":
                return "D";
            case "Boolean":
                return "B";
            default:
                return "A";
        }
    }
}
