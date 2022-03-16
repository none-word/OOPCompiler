package com.example.compiler.generator.model;

import com.example.compiler.generator.VmOperations;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Instruction {

    private int index;
    private VmOperations opcode;
    private List<String> operations;

}
