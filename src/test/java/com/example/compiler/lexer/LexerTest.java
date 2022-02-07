package com.example.compiler.lexer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(TokenType.KEYWORD, tokens[0].getType());
        assertEquals("var", tokens[0].getLexeme());
    }
}
