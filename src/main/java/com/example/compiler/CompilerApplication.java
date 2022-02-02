package com.example.compiler;

import com.example.compiler.lexer.InvalidTokenException;
import com.example.compiler.lexer.Lexer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class CompilerApplication {

    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = CompilerApplication.class.getClassLoader();
        String program = Objects.requireNonNull(classLoader.getResource("CodeExample.txt")).getFile();
        //System.out.println(program);

        Lexer lexer = new Lexer();
        try {
            var tokens = lexer.run(program);
            System.out.println(Arrays.toString(tokens));
        }
        catch (InvalidTokenException e){
            System.out.println(e.getMessage());
        }
    }

}
