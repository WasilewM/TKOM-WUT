# Instrukcja korzystania

W celu wykorzystania języka wystarczy zaimportować odpowiednie moduły znajdujące się na wydziałowym repozytorium
na [GitLabie](https://gitlab-stud.elka.pw.edu.pl/TKOM_23L_AM/Mateusz_Wasilewski/23l-tkom.git).  
Poniżej znajduje się przykład uruchomienia wykorzystany do przygotowania sekcji [Przykłady kody](./code_examples.md)

```
package visitors;

import lexer.Lexer;
import parser.Parser;
import parser.ParserErrorHandler;
import parser.program_components.Program;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class InterpreterDemo {

    public static void main(String[] args) throws FileNotFoundException {
        String filename = "src/visitors/code_examples/example_draw_scene.txt";
        ParserErrorHandler errorHandler = new ParserErrorHandler();
        if (args.length == 1) {
            filename = "src/visitors/code_examples/example_draw_scene.txt" + args[0];
        }
        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);
        Lexer myLexer = new Lexer(br);
        Parser myParser = new Parser(myLexer, errorHandler);
        Program program = myParser.parse();
        ContextManager contextManager = new ContextManager();
        InterpreterErrorHandler interpreterErrorHandler = new InterpreterErrorHandler();
        IVisitor visitor = new Interpreter(interpreterErrorHandler, contextManager);
        program.accept(visitor);
    }
}
```

Podczas korzystania z dostarczonego interpretera należy pamiętać o następujących rzeczach:

- program zaczyna się od wykonania funkcji `main` i funkcja ta jest wymagana, aby program był interpretowany
- kolejne funkcje są interpretowane w kolejności wywołań
- plik *nie może* kończyć się pustą linią i wszelkie miały znaki z końca pliku powinny zostać usunięte