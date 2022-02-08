package com.example.compiler.syntaxAnalysis;

import com.example.compiler.lexer.Token;
import java.util.List;

public class GrammarChecker {

    public final List<Token> tokens;
    int currentIndex, size;

    public GrammarChecker(List<Token> tokens) {
        this.tokens = tokens;
        currentIndex = 0;
        size = tokens.size();
    }

    private void verifyToken(Token token, String lexeme) {
        if (!token.getLexeme().equals(lexeme)) {
            throw new CompilationException();
        }
    }

//    public boolean isClassDeclaration() {
//        verifyToken(tokens.get(0), "class");
//
//    }
//
//    public int getClassNameEndingIndex() {
//
//    }



}
