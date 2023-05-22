package visitors;

import lexer.Lexer;
import parser.ParserErrorHandler;
import parser.Parser;
import parser.program_components.Program;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        String filename = "src/visitors/example.surface";
        ParserErrorHandler errorHandler = new ParserErrorHandler();
        if (args.length == 1) {
            filename = "src/visitors/example.surface" + args[0];
        }
        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);
        Lexer myLexer = new Lexer(br);
        Parser myParser = new Parser(myLexer, errorHandler);
        Program program = myParser.parse();
        IVisitor visitor = new ProgramPrinterVisitor();
        program.accept(visitor);
    }
}
