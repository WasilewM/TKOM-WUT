package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IFunctionDef;
import parser.program_components.CodeBlock;
import parser.program_components.Identifier;
import parser.program_components.Program;
import parser.program_components.data_values.IntValue;
import parser.program_components.function_definitions.IntFunctionDef;
import parser.program_components.parameters.IntParameter;
import parser.program_components.parameters.ReassignedParameter;
import parser.program_components.statements.AssignmentStatement;
import parser.program_components.statements.ReturnStatement;
import parser.program_components.statements.WhileStatement;
import visitors.exceptions.IdentifierNotFoundException;
import visitors.exceptions.NullExpressionException;
import visitors.utils.MockedContextDeletionInterpreter;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitMalformedWhileStatementTest {
    private static void assertErrorLogs(MockedExitInterpreterErrorHandler errorHandler, MockedContextDeletionInterpreter interpreter, Program program, List<Exception> expectedErrorLog) {
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
    void givenWhileStmnt_whenConditionIsNull_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new WhileStatement(new Position(20, 20), null, new CodeBlock(new Position(25, 25), new ArrayList<>())),
                    new ReturnStatement(new Position(50, 50), new IntValue(new Position(50, 60), 0))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new NullExpressionException(new WhileStatement(new Position(20, 20), null, new CodeBlock(new Position(25, 25), new ArrayList<>())))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }


    @Test
    void givenWhileStmnt_whenUnknownIdentifierInCondition_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new IntParameter(new Position(15, 15), "a"), new IntValue(new Position(15, 20), 1)),
                    new WhileStatement(new Position(20, 20), new Identifier(new Position(20, 25), "b"), new CodeBlock(new Position(21, 21), List.of(
                            new AssignmentStatement(new Position(25, 25), new ReassignedParameter(new Position(25, 25), "a"), new IntValue(new Position(25, 30), 0))
                    ))),
                    new ReturnStatement(new Position(30, 30), new Identifier(new Position(32, 40), "a"))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IdentifierNotFoundException(new Identifier(new Position(20, 25), "b"))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }
}
