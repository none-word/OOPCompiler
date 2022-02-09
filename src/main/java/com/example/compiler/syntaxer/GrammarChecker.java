package com.example.compiler.syntaxer;

import com.example.compiler.lexer.Token;
import com.example.compiler.lexer.TokenType;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GrammarChecker {

    public final List<Token> tokens;
    private int currentIndex = 0;

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
            incrementIndex();
        }
    }

    private void verifyTokenType(TokenType tokenType) {
        if (!tokenType.equals(tokenType())) {
            throw new CompilationException();
        } else {
            incrementIndex();
        }
    }

    public void specifyClassDeclaration() {
        verifyToken("class");
        specifyClassName();
        if (lexeme().equals("extends")) {
            verifyToken("extends");
            specifyClassName();
        }
        verifyToken("is");
        verifyToken("{");
        while (true) {
            int validIndex = currentIndex;
            try {
                specifyMemberDeclaration();
            } catch (Exception e) {
                currentIndex = validIndex;
                break;
            }
        }
        verifyToken("}");
        verifyToken("end");
    }

    public void specifyClassName() {
        specifyIdentifier();
        if (lexeme().equals("[")) {
            verifyToken("[");
            specifyClassName();
            if (!lexeme().equals("]")) {
                throw new CompilationException();
            } else {
                incrementIndex();
            }
        }
    }

    private void incrementIndex() {
        currentIndex++;
        if (currentIndex == tokens.size()) {
            throw new CompilationException();
        }
    }

    public void specifyIdentifier() {
        if (tokenType() != TokenType.IDENTIFIER) {
            throw new CompilationException();
        } else {
            incrementIndex();
        }
    }

    public void specifyMemberDeclaration() {
        try {
            specifyVariableDeclaration();
        } catch (CompilationException e) {
            try {
                specifyMethodDeclaration();
            } catch (CompilationException exception) {
                specifyConstructorDeclaration();
            }
        }
    }

    public void specifyVariableDeclaration() {
        verifyToken("var");
        specifyIdentifier();
        verifyToken(":");
        specifyExpression();
    }

    public void specifyMethodDeclaration() {
        verifyToken("method");
        specifyIdentifier();
        if (lexeme().equals("(")) {
            specifyParameters();
        }
        if (lexeme().equals(":")) {
            verifyToken(":");
            specifyIdentifier();
        }
        verifyToken("is");
        specifyBody();
        verifyToken("end");
    }

    private void specifyAdditionalParameters() {
        while (lexeme().equals(",")) {
            specifyParameterDeclaration();
        }
    }

    public void specifyParameters() {
        verifyToken("(");
        specifyParameterDeclaration();
        specifyAdditionalParameters();
        verifyToken(")");
    }

    public void specifyParameterDeclaration() {
        specifyIdentifier();
        specifyClassName();
    }

    private void specifyBody() {
        while (true) {
            int validState = currentIndex;
            try {
                specifyVariableDeclaration();
            } catch (Exception exception) {
                currentIndex = validState;
                try {
                    specifyStatement();
                } catch (Exception e) {
                    currentIndex = validState;
                    break;
                }
            }
        }
    }
    public void specifyConstructorDeclaration() {
        verifyToken("this");
        if (lexeme().equals("(")) {
            specifyParameters();
        }
        verifyToken("is");
        specifyBody();
        verifyToken("end");
    }

    private void specifyStatement() {
        try {
            specifyAssignment();
        } catch (Exception exception) {
            try {
                specifyWhileLoop();
            } catch (Exception e) {
                try {
                    specifyIfStatement();
                } catch (Exception ex) {
                    specifyReturnStatement();
                }
            }
        }
    }

    private void specifyAssignment() {
        specifyIdentifier();
        verifyToken(":=");
        specifyExpression();
    }

    private void specifyWhileLoop() {
        verifyToken("while");
        specifyExpression();
        verifyToken("loop");
        specifyBody();
        verifyToken("end");
    }

    private void specifyIfStatement() {
        verifyToken("if");
        specifyExpression();
        verifyToken("then");
        specifyBody();
        if ("else".equals(lexeme())) {
            specifyBody();
        }
        verifyToken("end");
    }

    private void specifyReturnStatement() {
        verifyToken("return");
        try {
            specifyExpression();
        } catch (Exception ignored) {
            // ToDO: fix
        }
    }

    private void specifyExpression() {
        specifyPrimary();
        while (lexeme().equals(".")) {
            verifyToken(".");
            specifyIdentifier();
            try {
                specifyArguments();
            } catch (Exception exception) {
                break;
            }
        }
    }

    private void specifyArguments() {
        verifyToken("(");
        specifyExpression();
        while (lexeme().equals(",")) {
            verifyToken(",");
            specifyExpression();
        }
        verifyToken(")");
    }

    private void specifyPrimary() {
        try {
            verifyTokenType(TokenType.LITERAL);
        } catch (Exception exception) {
            try {
                verifyToken("this");
            } catch (Exception e) {
                specifyClassName();
            }
        }
    }
}