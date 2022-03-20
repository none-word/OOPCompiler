package com.example.compiler.generator;

import com.example.compiler.generator.model.JvmType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GeneratorUtil {

    /**
     *
     * @param type type of JVM element
     * @return JVM descriptor
     */
    public String computeDescriptor(JvmType type) {
        switch (type) {
            case REAL:
                return "D";
            case BOOLEAN:
                return "Z";
            case INTEGER:
                return "I";
            default:
                return "A";
        }
    }

    /**
     *
     * @param typeName String name of JVM element
     * @return JVM descriptor
     */
    public String computeDescriptor(String typeName) {
        switch (typeName) {
            case "Integer":
                return "I";
            case "Real":
                return "D";
            case "Boolean":
                return "Z";
            default:
                return "A";
        }
    }

}
