package com.example.compiler.generator;

import com.example.compiler.generator.model.Constructor;
import com.example.compiler.generator.model.Variable;
import com.example.compiler.syntaxer.Node;
import com.example.compiler.syntaxer.Tree;
import com.example.compiler.syntaxer.TreeUtil;
import com.example.compiler.utils.Pair;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

@UtilityClass
public class ClassFileGenerator {

    private final String pkg = "currentPackage";

    /**
     * Takes program and generate classFiles for each class in it
     * @param tree is a program
     */
    public void generateClassFiles(Tree tree) {
        splitProgramToClasses(tree).forEach(ClassFileGenerator::createClassFileForClass);
    }

    /**
     * Divide program to class-Nodes
     * @param tree is a whole program
     * @return list of classes
     */
    private List<Node> splitProgramToClasses(Tree tree) {
        return new ArrayList<>(tree.getRoot().getChildNodes());
    }

    /**
     * For each classNode creates .class file
     * @param node is a class-Node
     */
    private void createClassFileForClass(Node node) {
        // retrieving class name and parent-class if exists
        Pair<String, String> signature = TreeUtil.getClassSignature(node);
        String className = signature.getFirst();
        // Object.class is a default super-class if nothing specified in java
        String superClass = signature.getSecond() == null ? "java/lang/Object" : signature.getSecond();
        // retrieving of special element for class(variables, constructors, etc.)
        List<Variable> classVariables = TreeUtil.classVariables(node);
        List<Constructor> classConstructors = TreeUtil.getConstructors(node);
        ClassWriter cw = new ClassWriter(0);
        // .class fullname consists of its package + name
        String fullName = String.format("%s/%s", pkg, className);
        classConstructors.forEach(c -> c.setClassName(fullName));
        // class signature generation
        cw.visit(Opcodes.V11, Opcodes.ACC_PUBLIC, fullName, null, superClass, null);
        // class variables generation
        classVariables.forEach(variable -> VariableGenerator.generateVariablesForClass(variable, cw));
        // constructor generation
        classConstructors.forEach(c -> ConstructorGenerator.generateConstructor(c, cw));
        // method generation
        MethodGenerator.generateMethod(node, cw);
        // generating .class file from bytes with given name
        generateClassFileFromBytes(cw.toByteArray(), className);
    }

    /**
     * Generate .class file from bytes
     * @param byteInterpretation is a byte array of a future .class file
     * @param className is a class name
     */
    private void generateClassFileFromBytes(byte[] byteInterpretation, String className) {
        try (FileOutputStream outputStream = new FileOutputStream(String.format("%s.class", className))) {
            outputStream.write(byteInterpretation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
