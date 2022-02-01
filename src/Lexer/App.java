package Lexer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class App
{
    public static final String filePath = "C:/Users/regin/Desktop/Studying/OOPCompiler/src/Lexer/CodeExample.txt";

    public static void main( String[] args ) throws IOException {
        String program = Files.readString(Path.of(filePath));
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
