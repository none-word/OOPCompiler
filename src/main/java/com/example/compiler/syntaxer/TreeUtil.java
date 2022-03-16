package com.example.compiler.syntaxer;

import com.example.compiler.generator.model.Condition;
import com.example.compiler.generator.model.Variable;
import com.example.compiler.utils.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TreeUtil {

    private final Predicate<Node> isVariableDeclaration = node -> node.getType().equals(FormalGrammar.VARIABLE_DECLARATION);
    private final Predicate<Node> isIdentifier = node -> node.getType().equals(FormalGrammar.IDENTIFIER);
    private final Predicate<Node> isExpression = node -> node.getType().equals(FormalGrammar.EXPRESSION);
    private final Predicate<Node> isBody = node -> node.getType().equals(FormalGrammar.BODY);
    private final Function<Node, Stream<Node>> convertToChildNodes = nodes -> nodes.getChildNodes().stream();
    /**
     *
     * @param classNode is a class
     * @return pair of ClassName + Extended ClassName(can be null)
     */
    public Pair<String, String> getClassSignature(Node classNode) {
        List<String> names = new ArrayList<>(2);
        names.addAll(classNode.getChildNodes()
            .stream()
            .filter(node -> node.getType().equals(FormalGrammar.CLASS_NAME))
            .flatMap(convertToChildNodes)
            .map(Node::getValue)
            .collect(Collectors.toList()));
        return new Pair<>(names.get(0), names.get(1));
    }

    public List<Variable> localVariables(Node node) {
        List<Variable> variables = new ArrayList<>();
        List<Node> variableNodes = node.getChildNodes().stream()
            .filter(isVariableDeclaration)
            .flatMap(convertToChildNodes)
            .collect(Collectors.toList());
        List<String> names = variableNodes.stream()
            .filter(isIdentifier)
            .flatMap(convertToChildNodes)
            .map(Node::getValue)
            .collect(Collectors.toList());
        List<Node> declarations = variableNodes.stream().filter(isExpression).collect(Collectors.toList());

        for (int i = 0; i < variableNodes.size(); i++) {
            variables.add(new Variable(names.get(i), ""));
        }

        return variables;
    }

    public Condition conditionFromNode(Node node) {
        Node expression = node.getChildNodes().stream().filter(isExpression).findFirst().get();
        List<Node> bodies = node.getChildNodes().stream().filter(isBody).collect(Collectors.toList());
        Node trueBody = bodies.get(0);
        Node elseBody = bodies.size() == 2 ? bodies.get(1) : null;
        return new Condition(expression, trueBody, elseBody);
    }



}
