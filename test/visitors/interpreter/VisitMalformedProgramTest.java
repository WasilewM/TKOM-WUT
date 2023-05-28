package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.program_components.Program;
import visitors.ContextManager;
import visitors.Interpreter;
import visitors.exceptions.MissingMainFunctionException;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitMalformedProgramTest {
    @Test
    void givenProgram_whenNoMainFunctionExists_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        Program program = new Program(new Position(1, 1), new HashMap<>());
        List<Exception> expectedErrorLog = List.of(
                new MissingMainFunctionException(new HashMap<>())
        );
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
}
