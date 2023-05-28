package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IFunctionDef;
import parser.program_components.CodeBlock;
import parser.program_components.Program;
import parser.program_components.data_values.DoubleValue;
import parser.program_components.data_values.IntValue;
import parser.program_components.expressions.*;
import parser.program_components.function_definitions.DoubleFunctionDef;
import parser.program_components.function_definitions.IntFunctionDef;
import parser.program_components.statements.ReturnStatement;
import visitors.Interpreter;
import visitors.utils.MockedContextManager;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitArithmeticExpressionsTest {
    @Test
    void givenIntFuncAndAdditionExpReturned_whenBothSidesAreIntValues_thenLastResultIsEvaluatedIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
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
    void givenIntFuncAndAdditionExpReturned_whenLeftIsIntValueAndRightIsDoubleValue_thenLastResultIsEvaluatedToIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
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
    void givenIntFuncAndAdditionExpReturned_whenLeftIsDoubleValueAndRightIsIntValue_thenLastResultIsEvaluatedToIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
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
    void givenIntFuncAndAdditionExpReturned_whenBothSidesAreDoubleValues_thenLastResultIsEvaluatedToIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
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

    @Test
    void givenDoubleFuncAndAdditionExpReturned_whenBothSidesAreIntValues_thenLastResultIsEvaluatedIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        DoubleValue expectedLastResult = new DoubleValue(new Position(30, 40), 17.17);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new DoubleFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new AdditionExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 0.0), new DoubleValue(new Position(30, 50), 17.17)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenDoubleFuncAndAdditionExpReturned_whenLeftIsIntValueAndRightIsDoubleValue_thenLastResultIsEvaluatedToDoubleValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        DoubleValue expectedLastResult = new DoubleValue(new Position(30, 40), 7.99);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new DoubleFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new AdditionExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new DoubleValue(new Position(30, 50), 3.99)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenDoubleFuncAndAdditionExpReturned_whenLeftIsDoubleValueAndRightIsIntValue_thenLastResultIsEvaluatedToDoubleValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        DoubleValue expectedLastResult = new DoubleValue(new Position(30, 40), 11.76);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new DoubleFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new AdditionExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 6.76), new IntValue(new Position(30, 50), 5)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenDoubleFuncAndAdditionExpReturned_whenBothSidesAreDoubleValues_thenLastResultIsEvaluatedToDoubleValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        DoubleValue expectedLastResult = new DoubleValue(new Position(30, 40), 11.761);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new DoubleFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new AdditionExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 6.76), new DoubleValue(new Position(30, 50), 5.001)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIntFuncAndSubtractionExpReturned_whenBothSidesAreIntValues_thenLastResultIsEvaluatedIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(30, 40), 1);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new SubtractionExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new IntValue(new Position(30, 50), 3)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIntFuncAndSubtractionExpReturned_whenLeftIsIntValueAndRightIsDoubleValue_thenLastResultIsEvaluatedToIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(30, 40), 0);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new SubtractionExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new DoubleValue(new Position(30, 50), 3.99)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIntFuncAndSubtractionExpReturned_whenLeftIsDoubleValueAndRightIsIntValue_thenLastResultIsEvaluatedToIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(30, 40), 1);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new SubtractionExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 6.76), new IntValue(new Position(30, 50), 5)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIntFuncAndSubtractionExpReturned_whenBothSidesAreDoubleValues_thenLastResultIsEvaluatedToIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(30, 40), 1);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new SubtractionExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 6.76), new DoubleValue(new Position(30, 50), 5.001)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIntFuncAndDiscreteDivisionExpReturned_whenBothSidesAreIntValues_thenLastResultIsEvaluatedIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(30, 40), 1);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new DiscreteDivisionExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new IntValue(new Position(30, 50), 3)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIntFuncAndDiscreteDivisionExpReturned_whenLeftIsIntValueAndRightIsDoubleValue_thenLastResultIsEvaluatedToIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(30, 40), 2);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new DiscreteDivisionExpression(new Position(30, 40), new IntValue(new Position(30, 40), 6), new DoubleValue(new Position(30, 50), 3.99)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIntFuncAndDiscreteDivisionExpReturned_whenLeftIsDoubleValueAndRightIsIntValue_thenLastResultIsEvaluatedToIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(30, 40), 3);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new DiscreteDivisionExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 6.76), new IntValue(new Position(30, 50), 2)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIntFuncAndDiscreteDivisionExpReturned_whenBothSidesAreDoubleValues_thenLastResultIsEvaluatedToIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(30, 40), 1);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new DiscreteDivisionExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 6.76), new DoubleValue(new Position(30, 50), 5.001)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenDoubleFuncAndDiscreteDivisionExpReturned_whenBothSidesAreIntValues_thenLastResultIsEvaluatedIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        DoubleValue expectedLastResult = new DoubleValue(new Position(30, 40), 1.0);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new DoubleFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new DiscreteDivisionExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new IntValue(new Position(30, 50), 3)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenDoubleFuncAndDiscreteDivisionExpReturned_whenLeftIsIntValueAndRightIsDoubleValue_thenLastResultIsEvaluatedToIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        DoubleValue expectedLastResult = new DoubleValue(new Position(30, 40), 2.0);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new DoubleFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new DiscreteDivisionExpression(new Position(30, 40), new IntValue(new Position(30, 40), 6), new DoubleValue(new Position(30, 50), 3.99)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenDoubleFuncAndDiscreteDivisionExpReturned_whenLeftIsDoubleValueAndRightIsIntValue_thenLastResultIsEvaluatedToIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        DoubleValue expectedLastResult = new DoubleValue(new Position(30, 40), 3.0);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new DoubleFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new DiscreteDivisionExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 6.76), new IntValue(new Position(30, 50), 2)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenDoubleFuncAndDiscreteDivisionExpReturned_whenBothSidesAreDoubleValues_thenLastResultIsEvaluatedToIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        DoubleValue expectedLastResult = new DoubleValue(new Position(30, 40), 1.0);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new DoubleFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new DiscreteDivisionExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 6.76), new DoubleValue(new Position(30, 50), 5.001)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIntFuncAndDivisionExpReturned_whenBothSidesAreIntValues_thenLastResultIsEvaluatedIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(30, 40), 1);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new DivisionExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new IntValue(new Position(30, 50), 3)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIntFuncAndDivisionExpReturned_whenLeftIsIntValueAndRightIsDoubleValue_thenLastResultIsEvaluatedToIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(30, 40), 1);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new DivisionExpression(new Position(30, 40), new IntValue(new Position(30, 40), 6), new DoubleValue(new Position(30, 50), 3.99)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIntFuncAndDivisionExpReturned_whenLeftIsDoubleValueAndRightIsIntValue_thenLastResultIsEvaluatedToIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(30, 40), 3);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new DivisionExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 6.76), new IntValue(new Position(30, 50), 2)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIntFuncAndDivisionExpReturned_whenBothSidesAreDoubleValues_thenLastResultIsEvaluatedToIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(30, 40), 1);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new DivisionExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 6.76), new DoubleValue(new Position(30, 50), 5.001)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenDoubleFuncAndDivisionExpReturned_whenBothSidesAreIntValues_thenLastResultIsEvaluatedIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        DoubleValue expectedLastResult = new DoubleValue(new Position(30, 40), 1.25);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new DoubleFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new DivisionExpression(new Position(30, 40), new IntValue(new Position(30, 40), 5), new IntValue(new Position(30, 50), 4)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenDoubleFuncAndDivisionExpReturned_whenLeftIsIntValueAndRightIsDoubleValue_thenLastResultIsEvaluatedToIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        DoubleValue expectedLastResult = new DoubleValue(new Position(30, 40), 1.50);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new DoubleFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new DivisionExpression(new Position(30, 40), new IntValue(new Position(30, 40), 6), new DoubleValue(new Position(30, 50), 4.00)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenDoubleFuncAndDivisionExpReturned_whenLeftIsDoubleValueAndRightIsIntValue_thenLastResultIsEvaluatedToIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        DoubleValue expectedLastResult = new DoubleValue(new Position(30, 40), 3.33);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new DoubleFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new DivisionExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 6.66), new IntValue(new Position(30, 50), 2)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenDoubleFuncAndDivisionExpReturned_whenBothSidesAreDoubleValues_thenLastResultIsEvaluatedToIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        DoubleValue expectedLastResult = new DoubleValue(new Position(30, 40), 1.3);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new DoubleFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new DivisionExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 6.50), new DoubleValue(new Position(30, 50), 5.00)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIntFuncAndMultiplicationExpReturned_whenBothSidesAreIntValues_thenLastResultIsEvaluatedIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(30, 40), 12);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new MultiplicationExpression(new Position(30, 40), new IntValue(new Position(30, 40), 4), new IntValue(new Position(30, 50), 3)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIntFuncAndMultiplicationExpReturned_whenLeftIsIntValueAndRightIsDoubleValue_thenLastResultIsEvaluatedToIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(30, 40), 23);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new MultiplicationExpression(new Position(30, 40), new IntValue(new Position(30, 40), 6), new DoubleValue(new Position(30, 50), 3.99)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIntFuncAndMultiplicationExpReturned_whenLeftIsDoubleValueAndRightIsIntValue_thenLastResultIsEvaluatedToIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(30, 40), 13);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new MultiplicationExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 6.76), new IntValue(new Position(30, 50), 2)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIntFuncAndMultiplicationExpReturned_whenBothSidesAreDoubleValues_thenLastResultIsEvaluatedToIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(30, 40), 33);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new MultiplicationExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 6.76), new DoubleValue(new Position(30, 50), 5.001)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenDoubleFuncAndMultiplicationExpReturned_whenBothSidesAreIntValues_thenLastResultIsEvaluatedIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        DoubleValue expectedLastResult = new DoubleValue(new Position(30, 40), 20.0);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new DoubleFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new MultiplicationExpression(new Position(30, 40), new IntValue(new Position(30, 40), 5), new IntValue(new Position(30, 50), 4)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenDoubleFuncAndMultiplicationExpReturned_whenLeftIsIntValueAndRightIsDoubleValue_thenLastResultIsEvaluatedToIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        DoubleValue expectedLastResult = new DoubleValue(new Position(30, 40), 23.94);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new DoubleFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new MultiplicationExpression(new Position(30, 40), new IntValue(new Position(30, 40), 6), new DoubleValue(new Position(30, 50), 3.99)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenDoubleFuncAndMultiplicationExpReturned_whenLeftIsDoubleValueAndRightIsIntValue_thenLastResultIsEvaluatedToIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        DoubleValue expectedLastResult = new DoubleValue(new Position(30, 40), 13.32);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new DoubleFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new MultiplicationExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 6.66), new IntValue(new Position(30, 50), 2)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenDoubleFuncAndMultiplicationExpReturned_whenBothSidesAreDoubleValues_thenLastResultIsEvaluatedToIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        DoubleValue expectedLastResult = new DoubleValue(new Position(30, 40), 34.17);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new DoubleFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new MultiplicationExpression(new Position(30, 40), new DoubleValue(new Position(30, 40), 6.7), new DoubleValue(new Position(30, 50), 5.1)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }
}
