package com.example.compiler.generator;

import com.example.compiler.syntaxer.Node;
import com.example.compiler.syntaxer.Tree;
import com.example.compiler.syntaxer.TreeUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ClassFileGenerator {

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
        String className = TreeUtil.getClassSignature(node).getFirst();
        File file = new File(className + ".txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // todo: implement logic
    }

}
