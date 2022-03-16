package com.example.compiler.generator.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Constructor {

    List<Variable> parameters;
    List<Variable> variables;

}
