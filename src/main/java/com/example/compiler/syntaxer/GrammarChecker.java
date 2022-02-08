package com.example.compiler.syntaxer;

import com.example.compiler.lexer.Token;
import com.example.compiler.lexer.TokenType;

import java.util.List;

public class GrammarChecker {

    public final List<Token> tokens;
    int currentIndex, size;

    public GrammarChecker(List<Token> tokens) {
        this.tokens = tokens;
        currentIndex = 0;
        size = tokens.size();
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
            currentIndex++;
        }
    }

    private void verifyTokenByTokenType(TokenType tokenType) {
        if (!tokenType.equals(tokenType())) {
            throw new CompilationException();
        } else {
            currentIndex++;
        }
    }

//    public boolean isClassDeclaration() {
//        verifyToken(tokens.get(0), "class");
//
//    }
//
    public void specifyClassName() {
        specifyIdentifier();
        if (lexeme().equals("[")) {
            currentIndex++;
            specifyClassName();
            if (!lexeme().equals("]")) {
                throw new CompilationException();
            } else {
                currentIndex++;
            }
        }
    }

    public void specifyIdentifier() {
        if (tokens.get(currentIndex).getType() != TokenType.IDENTIFIER) {
            throw new CompilationException();
        } else {
            currentIndex++;
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

    }

    public void specifyMethodDeclaration() {

    }

    public void specifyConstructorDeclaration() {

    }

    private void specifExpression() {
    }

    private void specifyArguments() {
        verifyToken("(");

    }

    private void specifyPrimary() {
        try {
            verifyTokenByTokenType(TokenType.LITERAL);
        } catch (Exception exception) {
            try {
                verifyToken("this");
            } catch (Exception e) {
                specifyClassName();
            }
        }
    }
}