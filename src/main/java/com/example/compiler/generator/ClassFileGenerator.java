package com.example.compiler.generator;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;

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
        Pair<String, String> signature = TreeUtil.getClassSignature(node);
        String className = signature.getFirst();
        String superClass = signature.getSecond() == null ? "java/lang/Object" : signature.getSecond();
        List<Variable> classVariables = TreeUtil.classVariables(node);
        List<Constructor> classConstructors = TreeUtil.getConstructors(node);
        ClassWriter cw = new ClassWriter(0);
        String fullName = String.format("%s/%s", pkg, className);
        classConstructors.forEach(c -> c.setClassName(fullName));
        // class signature
        cw.visit(Opcodes.V11, Opcodes.ACC_PUBLIC, fullName, null, superClass, null);
        // class variables
        for (Variable variable : classVariables) {
            String descriptor = GeneratorUtil.computeDescriptor(variable.getType());
            cw.visitField(ACC_PUBLIC, variable.getName(), descriptor, null, 0).visitEnd();
        }
        classConstructors.forEach(c -> ConstructorGenerator.generateConstructor(c, cw));
        MethodGenerator.generateMethod(node, cw);

        byte[] b = cw.toByteArray();
        try (FileOutputStream outputStream = new FileOutputStream(String.format("%s.class", className))) {
            outputStream.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
