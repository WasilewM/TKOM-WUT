package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IErrorHandler;
import parser.IFunctionDef;
import parser.program_components.*;
import parser.program_components.data_values.IntListValue;
import parser.program_components.data_values.IntValue;
import parser.program_components.function_definitions.IntFunctionDef;
import parser.program_components.parameters.IntListParameter;
import parser.program_components.statements.AssignmentStatement;
import visitors.ContextManager;
import visitors.Interpreter;
import visitors.exceptions.UndefinedMethodCallException;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitMalformedObjectAccessTest {
    private static void assertErrorLogs(IErrorHandler errorHandler, Interpreter interpreter, Program program, List<Exception> expectedErrorLog) {
        boolean wasExceptionCaught = false;
        try {
            interpreter.visit(program);
        } catch (RuntimeException e) {
            wasExceptionCaught = true;
            Iterator<Exception> expected = expectedErrorLog.iterator();
            Iterator<Exception> actual = errorHandler.getErrorLog().iterator();
            assertEquals(expectedErrorLog.size(), errorHandler.getErrorLog().size());
            while (expected.hasNext() && actual.hasNext()) {
                assertEquals(expected.next().getMessage(), actual.next().getMessage());
            }
        }

        assert wasExceptionCaught;
    }

    @Test
    void givenObjectAccessStmntWithIntList_whenMethodNotImplemented_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        functions.put("main", new IntFunctionDef(new Position(50, 1), "main", new HashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new IntListParameter(new Position(60, 1), "myList"), new IntListValue(new Position(60, 10))),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "put"), new IntValue(new Position(65, 10), 57)))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new UndefinedMethodCallException(new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "put"), new IntValue(new Position(65, 10), 57))))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenObjectAccessStmntWithIntList_whenRightExpIsNotFunctionCall_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        functions.put("main", new IntFunctionDef(new Position(50, 1), "main", new HashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new IntListParameter(new Position(60, 1), "myList"), new IntListValue(new Position(60, 10))),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new Identifier(new Position(65, 8), "put"))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new UndefinedMethodCallException(new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new Identifier(new Position(65, 8), "put")))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }
}
