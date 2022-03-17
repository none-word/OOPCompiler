package com.example.compiler.generator;

import com.example.compiler.syntaxer.Node;
import lombok.experimental.UtilityClass;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

@UtilityClass
public class IfStatementGenerator {

    // Template from documentation, nihrena ne rabotaet
    public void generateIfStatement(MethodVisitor methodVisitor, Node node) {
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitVarInsn(ILOAD, 2);
        Label label = new Label();
        methodVisitor.visitJumpInsn(IFLT, label);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitFieldInsn(PUTFIELD, "pkg/Bean", "f", "I");
        Label end = new Label();
        methodVisitor.visitJumpInsn(GOTO, end);
        methodVisitor.visitLabel(label);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitLabel(end);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(2, 2);
        methodVisitor.visitEnd();
    }

}
