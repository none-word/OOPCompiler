package com.example.compiler.generator;

import com.example.compiler.syntaxer.FormalGrammar;
import com.example.compiler.syntaxer.Node;
import lombok.experimental.UtilityClass;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

@UtilityClass
public class BodyGenerator {

    /**
     *
     * @param methodVisitor is a MethodVisitor from member of our PL to generate
     * @param node is a member which body will be written to .class file
     */
    public void generateBody(MethodVisitor methodVisitor, Node node) {
        int numberOfLocalVariables = 0;
        methodVisitor.visitCode();
        for (Node n: node.getChildNodes()) {
            if (n.getType().equals(FormalGrammar.VARIABLE_DECLARATION)) {
                methodVisitor.visitTypeInsn(Opcodes.NEW, "a");
                methodVisitor.visitLdcInsn(1);
            }
        }
        methodVisitor.visitMaxs(15, 15);
    }

}
