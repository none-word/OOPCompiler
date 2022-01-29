import java.util.ArrayList;

public class Lexer {
    private ArrayList<String> words;
    public Token[] run (String program){
        words = splitProgram(program);
        var t = new Token[10];
        return t;
    }

    private ArrayList<String> splitProgram(String program) {
        program = program.replaceAll("\r\n", "\n");

        var buffer = new ArrayList<Character>();
        var words = new ArrayList<String>();

        for (var character : program.toCharArray()) {
            if (character == ' ' || character == '\n' || character == '\t') {

                if (buffer.size() > 0) {
                    words.add(buffer.toString());
                    buffer.clear();
                }
            }
            else {
                buffer.add(character);
            }
        }

        if (buffer.size() > 0){
            words.add(buffer.toString());
        }

        separateDeclarations(words);

        return words;
    }

    private void separateDeclarations(ArrayList<String> words) {
        for (int i = 0; i < words.size(); i++) {

        }
    }

    private void getSeparatorIndex(String word){
        
    }
}
