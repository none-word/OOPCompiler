package com.example.compiler;

import com.example.compiler.lexer.InvalidTokenException;
import com.example.compiler.lexer.Lexer;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
public class CompilerApplication {

    private static final String PROGRAM_NAME = "CodeExample.txt";

    public static void main(String[] args) {
        ClassLoader classLoader = CompilerApplication.class.getClassLoader();
        String program = Objects.requireNonNull(classLoader.getResource(PROGRAM_NAME)).getFile();
        log.debug("Program: {}", program);

        Lexer lexer = new Lexer();
        try {
            var tokens = lexer.run(program);
            String stringWithTokens = Arrays.toString(tokens);
            log.info("Array with tokens: {}", stringWithTokens);
        }
        catch (InvalidTokenException e){
            log.error(e.getMessage());
        }
    }

}
