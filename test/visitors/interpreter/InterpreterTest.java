package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IFunctionDef;
import parser.program_components.CodeBlock;
import parser.program_components.Program;
import parser.program_components.function_definitions.IntFunctionDef;
import visitors.Interpreter;
import visitors.utils.MockedContextDeletionInterpreter;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class InterpreterTest {
    @Test
    void givenInterpreterInitialized_whenThereIsNothingToBeInterpreted_thenLastContextIsNull() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        Interpreter interpreter = new Interpreter(errorHandler);
        assertNull(interpreter.getLastContext());
    }

    @Test
    void givenInterpreter_whenMainFunctionWithoutParams_thenSingleEmptyContextExpected() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), new ArrayList<>())));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);
        assertNotNull(interpreter.getLastContext());
    }
}
