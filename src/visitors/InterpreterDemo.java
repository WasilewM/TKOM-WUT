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
        String filename = "src/visitors/code_examples/example_self_recursion.txt";
        ParserErrorHandler errorHandler = new ParserErrorHandler();
        if (args.length == 1) {
            filename = "src/visitors/code_examples/example_self_recursion.txt" + args[0];
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
        System.out.println(interpreterErrorHandler.getErrorLog());
    }
}
