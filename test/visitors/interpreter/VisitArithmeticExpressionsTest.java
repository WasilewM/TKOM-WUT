package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IFunctionDef;
import parser.program_components.CodeBlock;
import parser.program_components.Program;
import parser.program_components.data_values.DoubleValue;
import parser.program_components.data_values.IntValue;
import parser.program_components.expressions.AdditionExpression;
import parser.program_components.function_definitions.IntFunctionDef;
import parser.program_components.statements.ReturnStatement;
import visitors.utils.MockedContextDeletionInterpreter;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitArithmeticExpressionsTest {
    @Test
    void givenIntFuncAndAdditiveExpReturned_whenBothSidesAreIntValues_thenLastResultEqualsCalculatedExpAsIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        IntValue expectedLastResult = new IntValue(new Position(30, 40), 7);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new AdditionExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new IntValue(new Position(30, 50), 3)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIntFuncAndAdditiveExpReturned_whenLeftIsIntValueAndRightIsDoubleValue_thenDoubleValueIsImplicitlyCastedToIntValueAndLastResultEqualsCalculatedExpAsIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        IntValue expectedLastResult = new IntValue(new Position(30, 40), 7);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new AdditionExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new DoubleValue(new Position(30, 50), 3.99)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIntFuncAndAdditiveExpReturned_whenLeftIsDoubleValueAndRightIsIntValue_thenDoubleValueIsImplicitlyCastedToIntValueAndLastResultEqualsCalculatedExpAsIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        IntValue expectedLastResult = new IntValue(new Position(30, 40), 11);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new AdditionExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 6.76), new IntValue(new Position(30, 50), 5)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIntFuncAndAdditiveExpReturned_whenBothSidesAreDoubleValues_thenDoubleValuesAreImplicitlyCastedToIntValueAndLastResultEqualsCalculatedExpAsIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        IntValue expectedLastResult = new IntValue(new Position(30, 40), 11);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new AdditionExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 6.76), new DoubleValue(new Position(30, 50), 5.001)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }
}
