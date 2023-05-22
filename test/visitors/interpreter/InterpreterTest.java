package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IFunctionDef;
import parser.program_components.CodeBlock;
import parser.program_components.Program;
import parser.program_components.data_values.IntValue;
import parser.program_components.function_definitions.IntFunctionDef;
import parser.program_components.parameters.IntParameter;
import parser.program_components.statements.AssignmentStatement;
import visitors.Context;
import visitors.Interpreter;
import visitors.utils.MockedContextDeletionInterpreter;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void givenInterpreter_whenMainFunctionWithSingleParamDeclaration_thenContextWithSingleParamExpected() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new IntParameter(new Position(15, 15), "m"), new IntValue(new Position(15, 20), 2))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new IntValue(new Position(15, 20), 2));
        assertEquals(expected, interpreter.getLastContext());
    }
}
