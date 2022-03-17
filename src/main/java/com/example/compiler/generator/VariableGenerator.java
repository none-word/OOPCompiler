package com.example.compiler.generator;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;

import com.example.compiler.generator.model.Variable;
import lombok.experimental.UtilityClass;
import org.objectweb.asm.ClassWriter;

@UtilityClass
public class VariableGenerator {

    public void generateVariablesForClass(Variable variable, ClassWriter cw) {
        String descriptor = GeneratorUtil.computeDescriptor(variable.getType());
        cw.visitField(ACC_PUBLIC, variable.getName(), descriptor, null, 0).visitEnd();
    }

}
