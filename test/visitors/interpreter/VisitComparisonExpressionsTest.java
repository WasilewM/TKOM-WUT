package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IFunctionDef;
import parser.program_components.CodeBlock;
import parser.program_components.Program;
import parser.program_components.data_values.BoolValue;
import parser.program_components.data_values.DoubleValue;
import parser.program_components.data_values.IntValue;
import parser.program_components.expressions.EqualExpression;
import parser.program_components.expressions.GreaterThanExpression;
import parser.program_components.expressions.NotEqualExpression;
import parser.program_components.function_definitions.BoolFunctionDef;
import parser.program_components.statements.ReturnStatement;
import visitors.utils.MockedContextDeletionInterpreter;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitComparisonExpressionsTest {
    @Test
    void givenBoolFuncAndComparisonEqExpReturned_whenBothSideValuesAreEqual_thenLastResultIsEvaluatedToTrueBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), true);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new EqualExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new IntValue(new Position(30, 50), 4)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonEqExpReturned_whenBothSideValuesAreDifferent_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new EqualExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new IntValue(new Position(30, 50), 6)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonEqExpReturned_whenBothSideValuesAreEqualBuOfDifferentType_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new EqualExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new DoubleValue(new Position(30, 50), 4.0)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonEqExpReturned_whenLeftExpIsBoolValueAndRightExpIsDoubleValue_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new EqualExpression(new Position(30, 40), new BoolValue(new Position(30, 40), false), new DoubleValue(new Position(30, 50), 4.69)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonNotEqExpReturned_whenBothSideValuesAreEqual_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new NotEqualExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new IntValue(new Position(30, 50), 4)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonNotEqExpReturned_whenBothSideValuesAreDifferent_thenLastResultIsEvaluatedToTrueBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), true);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new NotEqualExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new IntValue(new Position(30, 50), 6)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonNotEqExpReturned_whenBothSideValuesAreEqualBuOfDifferentType_thenLastResultIsEvaluatedToTrueBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), true);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new NotEqualExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new DoubleValue(new Position(30, 50), 4.0)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonNotEqExpReturned_whenLeftExpIsBoolValueAndRightExpIsDoubleValue_thenLastResultIsEvaluatedToTrueBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), true);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new NotEqualExpression(new Position(30, 40), new BoolValue(new Position(30, 40), false), new DoubleValue(new Position(30, 50), 4.69)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonGtExpReturned_whenBothSideValuesAreEqual_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new GreaterThanExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new IntValue(new Position(30, 50), 4)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonGtExpReturned_whenValuesAreIntAndLeftSideIsGreater_thenLastResultIsEvaluatedToTrueBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), true);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new GreaterThanExpression(new Position(30, 40), new IntValue(new Position(30, 40), 44), new IntValue(new Position(30, 50), 6)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonGtExpReturned_whenValuesAreIntAndRightSideIsGreater_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new GreaterThanExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new IntValue(new Position(30, 50), 6)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonGtExpReturned_whenValuesAreDoubleAndLeftSideIsGreater_thenLastResultIsEvaluatedToTrueBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), true);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new GreaterThanExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 104.11), new DoubleValue(new Position(30, 50), 10.99)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonGtExpReturned_whenValuesAreDoubleAndRightSideIsGreater_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new GreaterThanExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 54.68), new DoubleValue(new Position(30, 50), 61.13)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonGtExpReturned_whenBothSideValuesAreEqualBuOfDifferentType_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new GreaterThanExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new DoubleValue(new Position(30, 50), 4.0)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }
}
