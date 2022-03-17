package com.example.compiler.generator;

import com.example.compiler.generator.model.JvmType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GeneratorUtil {

    public String computeDescriptor(JvmType type) {
        switch (type) {
            case REAL:
                return "D";
            case BOOLEAN:
                return "B";
            case INTEGER:
                return "I";
            default:
                return "A";
        }
    }

    public String computeDescriptor(String typeName) {
        switch (typeName) {
            case "Integer":
                return "I";
            case "Real":
                return "D";
            case "Boolean":
                return "B";
            default:
                return "A";
        }
    }

}
