package com.example.compiler;

import com.example.compiler.generator.ClassFileGenerator;
import com.example.compiler.lexer.InvalidTokenException;
import com.example.compiler.lexer.Lexer;
import com.example.compiler.lexer.Token;
import com.example.compiler.syntaxer.GrammarChecker;
import com.example.compiler.syntaxer.Tree;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static org.objectweb.asm.Opcodes.*;

@Slf4j
public class CompilerApplication {

    private static final String PROGRAM_NAME = "CodeExample3.txt";

    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = CompilerApplication.class.getClassLoader();
        String program = new String(Objects.requireNonNull(
            classLoader.getResourceAsStream(PROGRAM_NAME))
                                        .readAllBytes());
        log.debug("Program: {}", program);

        Lexer lexer = new Lexer();
        Token[] tokens = new Token[0];
        try {
            tokens = lexer.run(program);
            String stringWithTokens = Arrays.toString(tokens);
            log.info("Array with tokens: {}", stringWithTokens);
        } catch (InvalidTokenException e) {
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

        ClassFileGenerator.generateClassFiles(tree);
    }

    public static void main1 (String[] args) throws IOException {
        final String className = "Test";
        ClassWriter cw = new ClassWriter(0);
        // public class <className> extends Object {}
        cw.visit(V11, ACC_PUBLIC, className, null, "java/lang/Object", null);
        cw.visitField(ACC_PUBLIC, "a", "I",
                null, 1).visitEnd();
        cw.visitField(ACC_PUBLIC, "b", "I",
                null, 2).visitEnd();
        cw.visitMethod(ACC_PUBLIC, "compareTo",
                "(Ljava/lang/Object;)I", null, null).visitEnd();
        // public static void main (String[] args) {}
        MethodVisitor mainMethodWriter = cw.visitMethod(ACC_PUBLIC, "plus",
                "()I", null, null);

        mainMethodWriter.visitCode();
        mainMethodWriter.visitVarInsn(ALOAD, 0);
        mainMethodWriter.visitFieldInsn(GETFIELD, className, "a", "I");
        mainMethodWriter.visitVarInsn(ALOAD, 0);
        mainMethodWriter.visitFieldInsn(GETFIELD, className, "b", "I");
        mainMethodWriter.visitInsn(IADD);

        mainMethodWriter.visitInsn(IRETURN);
        mainMethodWriter.visitMaxs(5, 5);
        mainMethodWriter.visitEnd();
//        // System.out
//        mainMethodWriter.visitFieldInsn(GETSTATIC, "java/lang/System", "out",
//                "Ljava/io/PrintStream;");
//        // "hello"
//        mainMethodWriter.visitLdcInsn("hello");
//        // System.out.println("hello");
//        mainMethodWriter.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
//                "(Ljava/lang/String;)V", false);
//        // return;
//        mainMethodWriter.visitInsn(RETURN);
//        mainMethodWriter.visitEnd();
//        mainMethodWriter.visitMaxs(-1, -1);
        cw.visitEnd();

        byte[] bytecode = cw.toByteArray();
//        ByteArrayClassLoader loader = new ByteArrayClassLoader();
//        Class<?> test = loader.defineClass(className, bytecode);
//        try {
//            test.getMethod(, String[].class).invoke(null, (Object) new String[0]);
//        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        ObjectMapper mapper = new ObjectMapper();
//        File file = new File("hello.class");
//        mapper.writeValue(file, cw);
        FileOutputStream stream = new FileOutputStream("Test.class");
        stream.write(bytecode);
    }

}


/**
 * A class loader with the ability to load class from bytecode arrays.
 */
final class ByteArrayClassLoader extends ClassLoader
{
    // ---------------------------------------------------------------------------------------------

    static {
        registerAsParallelCapable();
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Default reusable instance of the class loader.
     */
    public static final ByteArrayClassLoader INSTANCE = new ByteArrayClassLoader();

    // ---------------------------------------------------------------------------------------------

    /**
     * Given a class' (dot-separated) binary name and the bytecode array, load the class
     * and return the corresponding {@link Class} object.
     */
    public Class<?> defineClass (String binaryName, byte[] bytecode) {
        return defineClass(binaryName, bytecode, 0, bytecode.length);
    }

    // ---------------------------------------------------------------------------------------------
}