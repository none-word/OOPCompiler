package com.example.compiler.syntaxer;

import com.example.compiler.lexer.Token;
import com.example.compiler.lexer.TokenType;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GrammarChecker {

    private List<Token> tokens;
    private Tree tree;
    private int currentIndex = 0;

    public Tree checkGrammar(List<Token> tokens) {
        this.tokens = tokens;
        tree = new Tree();
        specifyClassDeclaration(tree.getRoot());
        return tree;
    }

    private void specifyClassDeclaration(Node parentNode) {
        Node node = tree.addNode(FormalGrammar.CLASS_DECLARATION, parentNode);
        verifyToken("class");
        specifyClassName(node);
        if (lexeme().equals("extends")) {
            verifyToken("extends");
            specifyClassName(node);
        }
        verifyToken("is");
        while (true) {
            int validIndex = currentIndex;
            try {
                specifyMemberDeclaration(node);
            } catch (Exception e) {
                currentIndex = validIndex;
                break;
            }
        }
        verifyToken("end");
    }

    public void specifyClassName(Node parentNode) {
        Node node = tree.addNode(FormalGrammar.CLASS_NAME, parentNode);
        specifyIdentifier(node);
        if (lexeme().equals("[")) {
            verifyToken("[");
            specifyClassName(node);
            if (!lexeme().equals("]")) {
                throw new CompilationException();
            }
        }
    }

    public void specifyIdentifier(Node parentNode) {
        tree.addNode(FormalGrammar.IDENTIFIER, lexeme(), parentNode);
        if (tokenType() != TokenType.IDENTIFIER) {
            throw new CompilationException();
        } else {
            incrementIndex();
        }
    }

    public void specifyMemberDeclaration(Node parentNode) {
        Node node = tree.addNode(FormalGrammar.MEMBER_DECLARATION, parentNode);
        try {
            specifyVariableDeclaration(node);
        } catch (CompilationException e) {
            try {
                specifyMethodDeclaration(node);
            } catch (CompilationException exception) {
                specifyConstructorDeclaration(node);
            }
        }
    }

    public void specifyVariableDeclaration(Node parentNode) {
        Node node = tree.addNode(FormalGrammar.VARIABLE_DECLARATION, parentNode);
        verifyToken("var");
        specifyIdentifier(node);
        verifyToken(":");
        specifyExpression(node);
    }

    public void specifyMethodDeclaration(Node parentNode) {
        Node node = tree.addNode(FormalGrammar.METHOD_DECLARATION, parentNode);
        verifyToken("method");
        specifyIdentifier(node);
        if (lexeme().equals("(")) {
            specifyParameters(node);
        }
        if (lexeme().equals(":")) {
            verifyToken(":");
            specifyIdentifier(node);
        }
        verifyToken("is");
        specifyBody(node);
        verifyToken("end");
    }

    public void specifyParameters(Node parentNode) {
        Node node = tree.addNode(FormalGrammar.PARAMETERS, parentNode);
        verifyToken("(");
        specifyParameterDeclaration(node);
        while (lexeme().equals(",")) {
            specifyParameterDeclaration(node);
        }
        verifyToken(")");
    }

    public void specifyParameterDeclaration(Node parentNode) {
        Node node = tree.addNode(FormalGrammar.PARAMETER_DECLARATION, parentNode);
        specifyIdentifier(node);
        specifyClassName(node);
    }

    private void specifyBody(Node parentNode) {
        Node node = tree.addNode(FormalGrammar.BODY, parentNode);
        while (true) {
            int validState = currentIndex;
            try {
                specifyVariableDeclaration(node);
            } catch (Exception exception) {
                currentIndex = validState;
                try {
                    specifyStatement(node);
                } catch (Exception e) {
                    currentIndex = validState;
                    break;
                }
            }
        }
    }

    public void specifyConstructorDeclaration(Node parentNode) {
        Node node = tree.addNode(FormalGrammar.CONSTRUCTOR_DECLARATION, parentNode);
        verifyToken("this");
        if (lexeme().equals("(")) {
            specifyParameters(node);
        }
        verifyToken("is");
        specifyBody(node);
        verifyToken("end");
    }

    private void specifyStatement(Node parentNode) {
        Node node = tree.addNode(FormalGrammar.STATEMENT, parentNode);
        try {
            specifyAssignment(node);
        } catch (Exception exception) {
            try {
                specifyWhileLoop(node);
            } catch (Exception e) {
                try {
                    specifyIfStatement(node);
                } catch (Exception ex) {
                    specifyReturnStatement(node);
                }
            }
        }
    }

    private void specifyAssignment(Node parentNode) {
        Node node = tree.addNode(FormalGrammar.ASSIGNMENT, parentNode);
        specifyIdentifier(node);
        verifyToken(":=");
        specifyExpression(node);
    }

    private void specifyWhileLoop(Node parentNode) {
        Node node = tree.addNode(FormalGrammar.WHILE_LOOP, parentNode);
        verifyToken("while");
        specifyExpression(node);
        verifyToken("loop");
        specifyBody(node);
        verifyToken("end");
    }

    private void specifyIfStatement(Node parentNode) {
        Node node = tree.addNode(FormalGrammar.IF_STATEMENT, parentNode);
        verifyToken("if");
        specifyExpression(node);
        verifyToken("then");
        specifyBody(node);
        if ("else".equals(lexeme())) {
            specifyBody(node);
        }
        verifyToken("end");
    }

    private void specifyReturnStatement(Node parentNode) {
        Node node = tree.addNode(FormalGrammar.RETURN_STATEMENT, parentNode);
        verifyToken("return");
        try {
            specifyExpression(node);
        } catch (Exception ignored) {
            // ToDO: fix
        }
    }

    private void specifyExpression(Node parentNode) {
        Node node = tree.addNode(FormalGrammar.EXPRESSION, parentNode);
        specifyPrimary(node);
        while (lexeme().equals(".")) {
            verifyToken(".");
            specifyIdentifier(node);
            try {
                specifyArguments(node);
            } catch (Exception exception) {
                break;
            }
        }
    }

    private void specifyArguments(Node parentNode) {
        Node node = tree.addNode(FormalGrammar.ARGUMENTS, parentNode);
        verifyToken("(");
        specifyExpression(node);
        while (lexeme().equals(",")) {
            verifyToken(",");
            specifyExpression(node);
        }
        verifyToken(")");
    }

    private void specifyPrimary(Node parentNode) {
        Node node = tree.addNode(FormalGrammar.PRIMARY, parentNode);
        try {
            verifyTokenType(TokenType.LITERAL);
        } catch (Exception exception) {
            try {
                verifyToken("this");
            } catch (Exception e) {
                specifyClassName(node);
            }
        }
    }

    private String lexeme() {
        return tokens.get(currentIndex).getLexeme();
    }

    private TokenType tokenType() {
        return tokens.get(currentIndex).getType();
    }

    private void verifyToken(String lexeme) {
        if (!lexeme().equals(lexeme)) {
            throw new CompilationException();
        } else {
            try {
                incrementIndex();
            } catch (Exception ignored) {
            }
        }
    }

    private void verifyTokenType(TokenType tokenType) {
        if (!tokenType.equals(tokenType())) {
            throw new CompilationException();
        } else {
            incrementIndex();
        }
    }

    private void incrementIndex() {
        currentIndex++;
        if (currentIndex == tokens.size()) {
            throw new CompilationException();
        }
    }
}