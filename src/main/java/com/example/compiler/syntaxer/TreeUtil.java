package com.example.compiler.syntaxer;

import com.example.compiler.utils.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TreeUtil {

    /**
     *
     * @param classNode is a class
     * @return pair of ClassName + Extended ClassName(can be null)
     */
    public Pair<String, String> getClassSignature(Node classNode) {
        List<String> names = new ArrayList<>(2);
        names.addAll(classNode.getChildNodes()
            .stream()
            .filter(node -> node.getType().equals(FormalGrammar.CLASS_NAME))
            .flatMap(node -> node.getChildNodes().stream())
            .map(Node::getValue)
            .collect(Collectors.toList()));
        return new Pair<>(names.get(0), names.get(1));
    }

}
