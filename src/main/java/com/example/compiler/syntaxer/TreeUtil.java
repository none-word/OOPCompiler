package com.example.compiler.syntaxer;

import com.example.compiler.generator.model.Constructor;
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
    private final Predicate<Node> isMember = node -> node.getType().equals(FormalGrammar.MEMBER_DECLARATION);
    private final Predicate<Node> isConstructor = node -> node.getType().equals(FormalGrammar.CONSTRUCTOR_DECLARATION);
    private final Predicate<Node> isParameter = node -> node.getType().equals(FormalGrammar.PARAMETERS);
    private final Predicate<Node> isParameterDeclaration = node -> node.getType().equals(FormalGrammar.PARAMETER_DECLARATION);
    private final Predicate<Node> isClassName = node -> node.getType().equals(FormalGrammar.CLASS_NAME);
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
        if (names.size() == 1) {
            names.add(null);
        }
        return new Pair<>(names.get(0), names.get(1));
    }

    public List<Variable> classVariables(Node node) {
        List<Variable> variables = new ArrayList<>();
        List<Node> variableNodes = node.getChildNodes().stream()
            .filter(isMember)
            .flatMap(convertToChildNodes)
            .filter(isVariableDeclaration)
            .collect(Collectors.toList());
        List<String> names = variableNodes.stream()
            .flatMap(convertToChildNodes)
            .filter(isIdentifier)
            .map(Node::getValue)
            .collect(Collectors.toList());
        List<String> types = variableNodes.stream()
            .flatMap(convertToChildNodes)
            .filter(isExpression)
            .flatMap(convertToChildNodes)
            .map(Node::getValue)
            .collect(Collectors.toList());
        for (int i = 0; i < variableNodes.size(); i++) {
            variables.add(new Variable(names.get(i), types.get(i)));
        }
        return variables;
    }

    public List<Constructor> getConstructors(Node node) {
        List<Constructor> constructors = new ArrayList<>();
        List<Node> constructorNodes = node.getChildNodes().stream()
            .filter(isMember)
            .flatMap(convertToChildNodes)
            .filter(isConstructor)
            .collect(Collectors.toList());
        for (Node cons : constructorNodes) {
            // Collecting parameters
            List<Variable> parameters = new ArrayList<>();
            List<Node> parameterDeclarations = cons.getChildNodes().stream()
                .flatMap(convertToChildNodes)
                .filter(isParameterDeclaration)
                .flatMap(convertToChildNodes)
                .collect(Collectors.toList());
            List<String> parameterNames = parameterDeclarations.stream()
                .filter(isIdentifier)
                .map(Node::getValue)
                .collect(Collectors.toList());
            List<String> parameterTypes = parameterDeclarations.stream()
                .filter(isClassName)
                .flatMap(convertToChildNodes)
                .map(Node::getValue)
                .collect(Collectors.toList());
            for (int i = 0; i < parameterNames.size(); i++) {
                parameters.add(new Variable(parameterNames.get(i), parameterTypes.get(i)));
            }
            // Collecting Variable declarations inside constructor
            List<Variable> declaredVariables = new ArrayList<>();
            List<Node> variableNodes = cons.getChildNodes().stream()
                .flatMap(convertToChildNodes)
                .filter(isVariableDeclaration)
                .collect(Collectors.toList());
            List<String> names = variableNodes.stream()
                .flatMap(convertToChildNodes)
                .filter(isIdentifier)
                .map(Node::getValue)
                .collect(Collectors.toList());
            List<String> types = variableNodes.stream()
                .flatMap(convertToChildNodes)
                .filter(isExpression)
                .flatMap(convertToChildNodes)
                .map(Node::getValue)
                .collect(Collectors.toList());
            for (int i = 0; i < names.size(); i++) {
                declaredVariables.add(new Variable(names.get(i), types.get(i)));
            }
            constructors.add(new Constructor(parameters, declaredVariables));
        }
        return constructors;

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

}
