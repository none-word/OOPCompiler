package com.example.compiler.generator;

import static org.objectweb.asm.Opcodes.ALOAD;

import com.example.compiler.syntaxer.FormalGrammar;
import com.example.compiler.syntaxer.Node;
import lombok.experimental.UtilityClass;
import org.objectweb.asm.MethodVisitor;

@UtilityClass
public class BodyGenerator {

    /**
     *
     * @param methodVisitor is a MethodVisitor from member of our PL to generate
     * @param node is a
     */
    public void generateBody(MethodVisitor methodVisitor, Node node) {
        int numberOfLocalVariables = 0;
        methodVisitor.visitCode();
        for (Node n: node.getChildNodes()) {
            if (n.getType().equals(FormalGrammar.VARIABLE_DECLARATION)) {
                numberOfLocalVariables++;
                methodVisitor.visitVarInsn(ALOAD, numberOfLocalVariables + 5);
            }
        }
        methodVisitor.visitMaxs(15, 15);
    }

}
