package com.example.compiler.generator;

import com.example.compiler.syntaxer.Node;
import lombok.experimental.UtilityClass;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.compiler.syntaxer.FormalGrammar.*;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;

@UtilityClass
public class MethodGenerator {

    public void generateMethod(Node node, ClassWriter classWriter) {
        node.getChildNodes().stream()
                .filter(n -> MEMBER_DECLARATION.equals(n.getType()))
                .map(n -> n.getChildNodes().get(0))
                .filter(n -> METHOD_DECLARATION.equals(n.getType()))
                .forEach(m -> getMethodVisitor(m, classWriter));
    }

    private void getMethodVisitor(Node method, ClassWriter classWriter) {
        List<String> returnTypes = method.getChildNodes().stream()
                .filter(n -> IDENTIFIER.equals(n.getType()))
                .map(Node::getValue)
                .collect(Collectors.toList());
        String returnType = returnTypes.size() != 1 ? returnTypes.get(1) : "V";
        MethodVisitor methodVisitor = classWriter.visitMethod(
                ACC_PUBLIC,
                method.getChildNodes().get(0).getValue(),
                ParametersGenerator.generateParameters(method.getChildNodes().get(1), returnType),
                null,
                null);
        BodyGenerator.generateBody(methodVisitor, method.getChildNodes().get(method.getChildNodes().size() - 1));
        methodVisitor.visitEnd();
    }

}
