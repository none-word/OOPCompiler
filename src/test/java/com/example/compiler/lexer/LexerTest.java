package com.example.compiler.lexer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class LexerTest {

    Lexer lexer;

    @BeforeEach
    void init() {
        lexer = new Lexer();
    }

    private String getProgram(int testNumber) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        Path path = Path.of("test_data", "test" + testNumber + ".txt");
        InputStream inputStream = classLoader.getResourceAsStream(String.valueOf(path));
        assert inputStream != null;
        return new String(inputStream.readAllBytes());
    }

    @Test
    void lexerTest01() throws InvalidTokenException, IOException {
        String program = getProgram(1);
        var tokens = lexer.run(program);
        String stringWithTokens = Arrays.toString(tokens);
        log.info("Array with tokens: {}", stringWithTokens);
        assertEquals(TokenType.KEYWORD, tokens[0].getType());
        assertEquals("var", tokens[0].getLexeme());
        assertEquals(TokenType.IDENTIFIER, tokens[1].getType());
        assertEquals("a", tokens[1].getLexeme());
        assertEquals(TokenType.OPERATOR, tokens[2].getType());
        assertEquals(":", tokens[2].getLexeme());
        assertEquals(TokenType.IDENTIFIER, tokens[3].getType());
        assertEquals("Array", tokens[3].getLexeme());
        assertEquals(TokenType.OPERATOR, tokens[4].getType());
        assertEquals("[", tokens[4].getLexeme());
    }

    @Test
    void lexerTest02() throws InvalidTokenException, IOException {
        String program = getProgram(2);
        var tokens = lexer.run(program);
        String stringWithTokens = Arrays.toString(tokens);
        log.info("Array with tokens: {}", stringWithTokens);
        assertEquals(TokenType.KEYWORD, tokens[0].getType());
        assertEquals("var", tokens[0].getLexeme());
        assertEquals("i", tokens[1].getLexeme());
        assertEquals("is", tokens[2].getLexeme());
        assertEquals("1", tokens[3].getLexeme());
        assertEquals("while", tokens[4].getLexeme());
        assertEquals("loop", tokens[10].getLexeme());
    }

    @Test
    void lexerTest03() throws InvalidTokenException, IOException {
        String program = getProgram(3);
        var tokens = lexer.run(program);
        String stringWithTokens = Arrays.toString(tokens);
        log.info("Array with tokens: {}", stringWithTokens);
        assertEquals(TokenType.KEYWORD, tokens[0].getType());
        assertEquals("i", tokens[1].getLexeme());
        assertEquals("while", tokens[8].getLexeme());
    }

    @Test
    void lexerTest04() throws InvalidTokenException, IOException {
        String program = getProgram(4);
        var tokens = lexer.run(program);
        String stringWithTokens = Arrays.toString(tokens);
        log.info("Array with tokens: {}", stringWithTokens);
    }

    @Test
    void lexerTest05() throws InvalidTokenException, IOException {
        String program = getProgram(5);
        var tokens = lexer.run(program);
        String stringWithTokens = Arrays.toString(tokens);
        log.info("Array with tokens: {}", stringWithTokens);
        assertEquals("T", tokens[18].getLexeme());

    }

    @Test
    void lexerTest06() throws InvalidTokenException, IOException {
        String program = getProgram(6);
        var tokens = lexer.run(program);
        String stringWithTokens = Arrays.toString(tokens);
        log.info("Array with tokens: {}", stringWithTokens);
        assertEquals(TokenType.IDENTIFIER, tokens[5].getType());

    }

    @Test
    void lexerTest07() throws InvalidTokenException, IOException {
        String program = getProgram(7);
        var tokens = lexer.run(program);
        String stringWithTokens = Arrays.toString(tokens);
        log.info("Array with tokens: {}", stringWithTokens);
        assertEquals(TokenType.IDENTIFIER, tokens[8].getType());

    }
    @Test
    void lexerTest08() throws InvalidTokenException, IOException {
        String program = getProgram(8);
        var tokens = lexer.run(program);
        String stringWithTokens = Arrays.toString(tokens);
        log.info("Array with tokens: {}", stringWithTokens);
        assertEquals(TokenType.KEYWORD, tokens[5].getType());

    }
}
