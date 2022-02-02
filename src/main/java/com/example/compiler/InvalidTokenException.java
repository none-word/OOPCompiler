package com.example.compiler;

public class InvalidTokenException extends Exception {
    private final String lexeme;

    public String getLexeme() {
        return lexeme;
    }

    public InvalidTokenException(String lexeme) {
        this.lexeme = lexeme;
    }

    @Override
    public String getMessage() {
        return String.format("Lexeme '%s' is invalid.", lexeme);
    }


}