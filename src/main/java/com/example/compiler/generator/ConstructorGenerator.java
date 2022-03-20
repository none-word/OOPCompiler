package com.example.compiler.generator;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.RETURN;

import com.example.compiler.generator.model.Constructor;
import com.example.compiler.generator.model.Variable;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;


@UtilityClass
public class ConstructorGenerator {

    /**
     *
     * @param constructor is a Java-object for constructor-member
     * @param cw is a ClassWriter to generate constructors for our .class-files
     */
    public void generateConstructor(Constructor constructor, ClassWriter cw) {
        String constructorDescriptor = computeDescriptor(constructor.getParameters());
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", constructorDescriptor, null, null);
        // Load 'this'
        mv.visitVarInsn(ALOAD, 0);
        // Cal super()
        mv.visitMethodInsn(INVOKESPECIAL, Type.getInternalName(Object.class), "<init>", "()V", false);
        // Load 'this'
        mv.visitVarInsn(ALOAD, 0);
        BodyGenerator.generateBody(mv, constructor.getBody());
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    /**
     *
     * @param parameters List of incoming variables
     * @return descriptor for constructor
     */
    private String computeDescriptor(List<Variable> parameters) {
        String typeDescriptors = parameters.stream()
            .map(Variable::getType)
            .map(GeneratorUtil::computeDescriptor)
            .collect(Collectors.joining());
        return String.format("(%s)V", typeDescriptors);
    }

}
