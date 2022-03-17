package com.example.compiler.generator;

import com.example.compiler.generator.model.Variable;
import com.example.compiler.syntaxer.Node;
import com.example.compiler.syntaxer.TreeUtil;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ParametersGenerator {

    public String generateParameters(Node node, String returnType) {
        return "(" + TreeUtil.getParameters(node).stream()
                .map(Variable::getTypeChar)
                .collect(Collectors.joining()) + ")" + GeneratorUtil.computeDescriptor(returnType);
    }
}
