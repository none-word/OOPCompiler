package com.example.compiler.generator;

import static com.example.compiler.syntaxer.FormalGrammar.IDENTIFIER;
import static com.example.compiler.syntaxer.FormalGrammar.MEMBER_DECLARATION;
import static com.example.compiler.syntaxer.FormalGrammar.METHOD_DECLARATION;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;

import com.example.compiler.syntaxer.Node;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

@UtilityClass
public class MethodGenerator {

    /**
     *
     * @param node is a member node to parse
     * @param classWriter is a global ClassWriter
     */
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
