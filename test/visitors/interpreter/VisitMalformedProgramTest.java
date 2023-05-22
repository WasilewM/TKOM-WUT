package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.program_components.Program;
import visitors.Interpreter;
import visitors.exceptions.MissingMainFunctionException;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitMalformedProgramTest {
    @Test
    void visitProgramWithoutMainFunction() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        Interpreter interpreter = new Interpreter(errorHandler);
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
