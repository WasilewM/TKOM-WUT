package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IFunctionDef;
import parser.program_components.CodeBlock;
import parser.program_components.Program;
import parser.program_components.data_values.BoolValue;
import parser.program_components.data_values.DoubleValue;
import parser.program_components.data_values.IntValue;
import parser.program_components.expressions.AdditionExpression;
import parser.program_components.expressions.DiscreteDivisionExpression;
import parser.program_components.expressions.DivisionExpression;
import parser.program_components.expressions.SubtractionExpression;
import parser.program_components.function_definitions.IntFunctionDef;
import parser.program_components.statements.ReturnStatement;
import visitors.exceptions.OperationDataTypeException;
import visitors.utils.MockedContextDeletionInterpreter;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitMalformedArithmeticExpressionsTest {
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
    void givenIntFuncAndAdditionExpReturned_whenLeftIsDoubleValueAndRightIsBoolValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new AdditionExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 6.76), new BoolValue(new Position(30, 50), false)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new OperationDataTypeException(new Position(30, 40), new DoubleValue(new Position(30, 40), 6.76), new BoolValue(new Position(30, 50), false))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenIntFuncAndAdditionExpReturned_whenLeftIsIsBoolValueAndRightIsIntValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new AdditionExpression(new Position(30, 40), new BoolValue(new Position(30, 50), false), new IntValue(new Position(30, 40), 19)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new OperationDataTypeException(new Position(30, 40), new BoolValue(new Position(30, 50), false), new IntValue(new Position(30, 40), 19))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenIntFuncAndSubtractionExpReturned_whenLeftIsDoubleValueAndRightIsBoolValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new SubtractionExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 6.76), new BoolValue(new Position(30, 50), false)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new OperationDataTypeException(new Position(30, 40), new DoubleValue(new Position(30, 40), 6.76), new BoolValue(new Position(30, 50), false))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenIntFuncAndSubtractionExpReturned_whenLeftIsIsBoolValueAndRightIsIntValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new SubtractionExpression(new Position(30, 40), new BoolValue(new Position(30, 50), false), new IntValue(new Position(30, 40), 19)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new OperationDataTypeException(new Position(30, 40), new BoolValue(new Position(30, 50), false), new IntValue(new Position(30, 40), 19))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenIntFuncAndDiscreteDivisionExpReturned_whenLeftIsIsBoolValueAndRightIsIntValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new DiscreteDivisionExpression(new Position(30, 40), new BoolValue(new Position(30, 50), false), new IntValue(new Position(30, 40), 19)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new OperationDataTypeException(new Position(30, 40), new BoolValue(new Position(30, 50), false), new IntValue(new Position(30, 40), 19))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenIntFuncAndDivisionExpReturned_whenLeftIsIsBoolValueAndRightIsIntValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new DivisionExpression(new Position(30, 40), new BoolValue(new Position(30, 50), false), new IntValue(new Position(30, 40), 19)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new OperationDataTypeException(new Position(30, 40), new BoolValue(new Position(30, 50), false), new IntValue(new Position(30, 40), 19))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }
}
