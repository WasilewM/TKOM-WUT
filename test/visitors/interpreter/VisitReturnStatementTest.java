package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IFunctionDef;
import parser.program_components.CodeBlock;
import parser.program_components.Program;
import parser.program_components.data_values.*;
import parser.program_components.function_definitions.*;
import parser.program_components.statements.ReturnStatement;
import visitors.utils.MockedContextDeletionInterpreter;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitReturnStatementTest {
    @Test
    void givenIntFunc_whenIntValueReturned_thenLastResultIsIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        IntValue expectedLastResult = new IntValue(new Position(30, 40), 0);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), expectedLastResult)
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIntFunc_whenDoubleValueReturned_thenLastResultIsImplicitlyCastedIntValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        IntValue expectedLastResult = new IntValue(new Position(30, 40), 7);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new DoubleValue(new Position(30, 40), 7.20))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIntListFunc_whenIntListValueReturned_thenLastResultIsIntListValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        IntListValue expectedLastResult = new IntListValue(new Position(30, 40));
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntListFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), expectedLastResult)
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenDoubleFunc_whenDoubleValueReturned_thenLastResultIsDoubleValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        DoubleValue expectedLastResult = new DoubleValue(new Position(30, 40), 3.14);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new DoubleFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), expectedLastResult)
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenDoubleFunc_whenIntValueReturned_thenLastResultIsImplicitlyCastedDoubleValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        DoubleValue expectedLastResult = new DoubleValue(new Position(30, 40), 4.0);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new DoubleFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 4))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenDoubleListFunc_whenDoubleListValueReturned_thenLastResultIsDoubleValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        DoubleListValue expectedLastResult = new DoubleListValue(new Position(30, 40));
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new DoubleListFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), expectedLastResult)
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolFunc_whenBoolValueReturned_thenLastResultIsBoolValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        BoolValue expectedLastResult = new BoolValue(new Position(30, 40), true);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), expectedLastResult)
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenBoolListFunc_whenBoolListValueReturned_thenLastResultIsBoolListValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        BoolListValue expectedLastResult = new BoolListValue(new Position(30, 40));
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new BoolListFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), expectedLastResult)
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenFigureFunc_whenFigureValueReturned_thenLastResultIsFigureValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        FigureValue expectedLastResult = new FigureValue(new Position(30, 40));
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new FigureFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), expectedLastResult)
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenFigureListFunc_whenFigureListValueReturned_thenLastResultIsFigureListValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        FigureListValue expectedLastResult = new FigureListValue(new Position(30, 40));
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new FigureListFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), expectedLastResult)
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenPointFunc_whenPointValueReturned_thenLastResultIsPointValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        PointValue expectedLastResult = new PointValue(new Position(30, 40), new DoubleValue(new Position(15, 20), 10.0), new DoubleValue(new Position(15, 25), 11.0));
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new PointFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), expectedLastResult)
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenPointListFunc_whenPointListValueReturned_thenLastResultIsPointListValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        PointListValue expectedLastResult = new PointListValue(new Position(30, 40));
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new PointListFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), expectedLastResult)
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenSceneFunc_whenSceneValueReturned_thenLastResultIsSceneValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        SceneValue expectedLastResult = new SceneValue(new Position(30, 40));
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new SceneFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), expectedLastResult)
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenSceneListFunc_whenSceneListValueReturned_thenLastResultIsSceneListValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        SceneListValue expectedLastResult = new SceneListValue(new Position(30, 40));
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new SceneListFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), expectedLastResult)
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }
}
