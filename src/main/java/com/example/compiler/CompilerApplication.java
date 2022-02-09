package com.example.compiler;

import com.example.compiler.lexer.InvalidTokenException;
import com.example.compiler.lexer.Lexer;
import com.example.compiler.lexer.Token;
import com.example.compiler.syntaxer.GrammarChecker;
import com.example.compiler.syntaxer.Tree;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
public class CompilerApplication {

    private static final String PROGRAM_NAME = "CodeExample2.txt";

    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = CompilerApplication.class.getClassLoader();
        String program = new String(Objects.requireNonNull(classLoader.getResourceAsStream(PROGRAM_NAME)).readAllBytes());
        log.debug("Program: {}", program);

        Lexer lexer = new Lexer();
        Token[] tokens = new Token[0];
        try {
            tokens = lexer.run(program);
            String stringWithTokens = Arrays.toString(tokens);
            log.info("Array with tokens: {}", stringWithTokens);
        }
        catch (InvalidTokenException e){
            log.error(e.getMessage());
        }

        GrammarChecker grammarChecker = new GrammarChecker();
        Tree tree = null;
        try {
            tree = grammarChecker.checkGrammar(Arrays.asList(tokens));
            log.info(tree.toString());
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("Tree.json");
        mapper.writeValue(file, tree);
    }

}
