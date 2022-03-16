package com.example.compiler.generator;

import com.example.compiler.generator.model.Variable;
import com.example.compiler.syntaxer.Node;
import com.example.compiler.syntaxer.Tree;
import com.example.compiler.syntaxer.TreeUtil;
import com.example.compiler.utils.Pair;
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
        ClassWriter cw = new ClassWriter(0);
        cw.visit(Opcodes.V11, Opcodes.ACC_PUBLIC, pkg, null, superClass, new String[0]);

    }

}
