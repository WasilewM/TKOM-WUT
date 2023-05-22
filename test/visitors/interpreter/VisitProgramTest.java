package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IFunctionDef;
import parser.program_components.CodeBlock;
import parser.program_components.Program;
import parser.program_components.function_definitions.IntFunctionDef;
import visitors.Interpreter;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitProgramTest {
    @Test
    void visitProgramWithMainFunction() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        Interpreter interpreter = new Interpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), new ArrayList<>())));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(0, errorHandler.getErrorLog().size());
    }
}
