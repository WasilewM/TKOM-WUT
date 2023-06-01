package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IFunctionDef;
import parser.program_components.CodeBlock;
import parser.program_components.Program;
import parser.program_components.data_values.BoolValue;
import parser.program_components.data_values.DoubleValue;
import parser.program_components.data_values.IntValue;
import parser.program_components.expressions.*;
import parser.program_components.function_definitions.BoolFunctionDef;
import parser.program_components.statements.ReturnStatement;
import visitors.Interpreter;
import visitors.utils.MockedContextManager;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitComparisonExpressionsTest {
    @Test
    void givenBoolFuncAndComparisonEqExpReturned_whenBothSideValuesAreEqual_thenLastResultIsEvaluatedToTrueBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), true);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new EqualExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new IntValue(new Position(30, 50), 4)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonEqExpReturned_whenBothSideValuesAreDifferent_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new EqualExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new IntValue(new Position(30, 50), 6)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonEqExpReturned_whenBothSideValuesAreEqualBuOfDifferentType_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new EqualExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new DoubleValue(new Position(30, 50), 4.0)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonEqExpReturned_whenLeftExpIsBoolValueAndRightExpIsDoubleValue_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new EqualExpression(new Position(30, 40), new BoolValue(new Position(30, 40), false), new DoubleValue(new Position(30, 50), 4.69)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonNotEqExpReturned_whenBothSideValuesAreEqual_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new NotEqualExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new IntValue(new Position(30, 50), 4)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonNotEqExpReturned_whenBothSideValuesAreDifferent_thenLastResultIsEvaluatedToTrueBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), true);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new NotEqualExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new IntValue(new Position(30, 50), 6)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonNotEqExpReturned_whenBothSideValuesAreEqualBuOfDifferentType_thenLastResultIsEvaluatedToTrueBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), true);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new NotEqualExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new DoubleValue(new Position(30, 50), 4.0)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonNotEqExpReturned_whenLeftExpIsBoolValueAndRightExpIsDoubleValue_thenLastResultIsEvaluatedToTrueBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), true);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new NotEqualExpression(new Position(30, 40), new BoolValue(new Position(30, 40), false), new DoubleValue(new Position(30, 50), 4.69)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonGtExpReturned_whenBothSideValuesAreEqual_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new GreaterThanExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new IntValue(new Position(30, 50), 4)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonGtExpReturned_whenValuesAreIntAndLeftSideIsGreater_thenLastResultIsEvaluatedToTrueBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), true);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new GreaterThanExpression(new Position(30, 40), new IntValue(new Position(30, 40), 44), new IntValue(new Position(30, 50), 6)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonGtExpReturned_whenValuesAreIntAndRightSideIsGreater_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new GreaterThanExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new IntValue(new Position(30, 50), 6)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonGtExpReturned_whenValuesAreDoubleAndLeftSideIsGreater_thenLastResultIsEvaluatedToTrueBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), true);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new GreaterThanExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 104.11), new DoubleValue(new Position(30, 50), 10.99)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonGtExpReturned_whenValuesAreDoubleAndRightSideIsGreater_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new GreaterThanExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 54.68), new DoubleValue(new Position(30, 50), 61.13)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonGtExpReturned_whenBothSideValuesAreEqualBuOfDifferentType_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new GreaterThanExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new DoubleValue(new Position(30, 50), 4.0)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonGeExpReturned_whenBothSideAreEqualInts_thenLastResultIsEvaluatedToTrueBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), true);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new GreaterOrEqualExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new IntValue(new Position(30, 50), 4)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonGeExpReturned_whenValuesAreIntAndLeftSideIsGreater_thenLastResultIsEvaluatedToTrueBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), true);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new GreaterOrEqualExpression(new Position(30, 40), new IntValue(new Position(30, 40), 44), new IntValue(new Position(30, 50), 6)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonGeExpReturned_whenValuesAreIntAndRightSideIsGreater_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new GreaterOrEqualExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new IntValue(new Position(30, 50), 6)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonGeExpReturned_whenBothSideAreEqualDoubles_thenLastResultIsEvaluatedToTrueBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), true);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new GreaterOrEqualExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 4.0), new DoubleValue(new Position(30, 50), 4.0)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonGeExpReturned_whenValuesAreDoubleAndLeftSideIsGreater_thenLastResultIsEvaluatedToTrueBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), true);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new GreaterOrEqualExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 104.11), new DoubleValue(new Position(30, 50), 10.99)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonGeExpReturned_whenValuesAreDoubleAndRightSideIsGreater_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new GreaterOrEqualExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 54.68), new DoubleValue(new Position(30, 50), 61.13)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonGeExpReturned_whenBothSideValuesAreEqualBuOfDifferentType_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new GreaterOrEqualExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new DoubleValue(new Position(30, 50), 4.0)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonLtExpReturned_whenBothSideValuesAreEqual_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new LessThanExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new IntValue(new Position(30, 50), 4)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonLtExpReturned_whenValuesAreIntAndLeftSideIsGreater_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new LessThanExpression(new Position(30, 40), new IntValue(new Position(30, 40), 44), new IntValue(new Position(30, 50), 6)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonLtExpReturned_whenValuesAreIntAndRightSideIsGreater_thenLastResultIsEvaluatedToTrueBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), true);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new LessThanExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new IntValue(new Position(30, 50), 6)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonLtExpReturned_whenValuesAreDoubleAndLeftSideIsGreater_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new LessThanExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 104.11), new DoubleValue(new Position(30, 50), 10.99)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonLtExpReturned_whenValuesAreDoubleAndRightSideIsGreater_thenLastResultIsEvaluatedToTrueBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), true);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new LessThanExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 54.68), new DoubleValue(new Position(30, 50), 61.13)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonLtExpReturned_whenBothSideValuesAreEqualBuOfDifferentType_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new LessThanExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new DoubleValue(new Position(30, 50), 4.0)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonLeExpReturned_whenBothSideAreEqualInts_thenLastResultIsEvaluatedToTrueBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), true);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new LessOrEqualExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new IntValue(new Position(30, 50), 4)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonLeExpReturned_whenValuesAreIntAndLeftSideIsGreater_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new LessOrEqualExpression(new Position(30, 40), new IntValue(new Position(30, 40), 44), new IntValue(new Position(30, 50), 6)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonLeExpReturned_whenValuesAreIntAndRightSideIsGreater_thenLastResultIsEvaluatedToTrueBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), true);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new LessOrEqualExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new IntValue(new Position(30, 50), 6)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonLeExpReturned_whenBothSideAreEqualDoubles_thenLastResultIsEvaluatedToTrueBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), true);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new LessOrEqualExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 4.0), new DoubleValue(new Position(30, 50), 4.0)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonLeExpReturned_whenValuesAreDoubleAndLeftSideIsGreater_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new LessOrEqualExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 104.11), new DoubleValue(new Position(30, 50), 10.99)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonLeExpReturned_whenValuesAreDoubleAndRightSideIsGreater_thenLastResultIsEvaluatedToTrueBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), true);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new LessOrEqualExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 54.68), new DoubleValue(new Position(30, 50), 61.13)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFuncAndComparisonLeExpReturned_whenBothSideValuesAreEqualBuOfDifferentType_thenLastResultIsEvaluatedToFalseBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), false);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new LessOrEqualExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new DoubleValue(new Position(30, 50), 4.0)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }
}
