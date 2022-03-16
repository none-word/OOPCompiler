package com.example.compiler.generator.model;

import java.util.List;
import lombok.Data;

@Data
public class MethodImplementation {
    List<Instruction> instructions;
    int stack;
    int locals;
    int argsSize;
    String descriptor;
    String flags;
}
