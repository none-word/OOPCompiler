package com.example.compiler.generator;

import com.example.compiler.generator.model.Variable;
import com.example.compiler.syntaxer.FormalGrammar;
import com.example.compiler.syntaxer.Node;
import lombok.experimental.UtilityClass;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.ALOAD;

@UtilityClass
public class BodyGenerator {

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

    public Variable getVariable(Node node) {
        return new Variable(node.getChildNodes().get(0).getValue(),
                node.getChildNodes().get(1) // get EXPRESSION
                        .getChildNodes().get(0) // get PRIMARY
                        .getChildNodes().get(0) // get CLASS_NAME
                        .getChildNodes().get(0) // get IDENTIFIER
                        .getValue());
    }

}
