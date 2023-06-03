package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.IFunctionDef;
import parser.program_components.*;
import parser.program_components.data_values.*;
import parser.program_components.function_definitions.VoidFunctionDef;
import parser.program_components.parameters.*;
import parser.program_components.statements.AssignmentStatement;
import visitors.Interpreter;
import visitors.utils.MockedContextManager;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitPrintFunctionTest {
    private ByteArrayOutputStream outStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpIOStreams() {
        System.setOut(new PrintStream(outStream));
    }

    @AfterEach
    public void clearIOStreams() {
        outStream = new ByteArrayOutputStream();
    }

    @Test
    void givenBoolValue_whenPrintingBoolValue_thenValueIsPrintedWithoutItsPosition() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new VoidFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new BoolParameter(new Position(15, 15), "m"), new BoolValue(new Position(15, 20), true)),
                    new ObjectAccess(new Position(20, 1), new Identifier(new Position(20, 1), "m"), new FunctionCall(new Position(20, 3), new Identifier(new Position(20, 1), "print")))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals("true", outStream.toString());
    }

    @Test
    void givenDoubleValue_whenPrintingDoubleValue_thenValueIsPrintedWithoutItsPosition() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new VoidFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new DoubleParameter(new Position(15, 15), "m"), new DoubleValue(new Position(15, 20), 2.73)),
                    new ObjectAccess(new Position(20, 1), new Identifier(new Position(20, 1), "m"), new FunctionCall(new Position(20, 3), new Identifier(new Position(20, 1), "print")))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals("2.73", outStream.toString());
    }

    @Test
    void givenFigureValue_whenPrintingEmptyFigure_thenValueIsPrintedWithoutItsPosition() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new VoidFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new FigureParameter(new Position(15, 15), "m"), new FigureValue(new Position(15, 20))),
                    new ObjectAccess(new Position(20, 1), new Identifier(new Position(20, 1), "m"), new FunctionCall(new Position(20, 3), new Identifier(new Position(20, 1), "print")))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals("Figure[]", outStream.toString());
    }

    @Test
    void givenFigureValue_whenPrintingFigureWithSectionAdded_thenValueIsPrintedWithoutItsPosition() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        SectionValue section = new SectionValue(new Position(17, 80), new PointValue(new Position(17, 1), new DoubleValue(new Position(17, 85), 100.4), new DoubleValue(new Position(17, 90), 1.4)), new PointValue(new Position(17, 100), new DoubleValue(new Position(17, 110), 22.3), new DoubleValue(new Position(17, 115), 15.0)));
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new VoidFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new FigureParameter(new Position(15, 15), "m"), new FigureValue(new Position(15, 20))),
                    new ObjectAccess(new Position(17, 1), new Identifier(new Position(17, 1), "m"), new FunctionCall(new Position(17, 3), new Identifier(new Position(17, 1), "add"), section)),
                    new ObjectAccess(new Position(20, 1), new Identifier(new Position(20, 1), "m"), new FunctionCall(new Position(20, 3), new Identifier(new Position(20, 1), "print")))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals("Figure[Section[Point(100.4, 1.4), Point(22.3, 15.0)]]", outStream.toString());
    }

    @Test
    void givenIntValue_whenPrintingIntValue_thenValueIsPrintedWithoutItsPosition() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new VoidFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new IntParameter(new Position(15, 15), "m"), new IntValue(new Position(15, 20), 2)),
                    new ObjectAccess(new Position(20, 1), new Identifier(new Position(20, 1), "m"), new FunctionCall(new Position(20, 3), new Identifier(new Position(20, 1), "print")))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals("2", outStream.toString());
    }

    @Test
    void givenPointValue_whenPrintingPoint_thenValueIsPrintedWithoutItsPosition() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        PointValue point = new PointValue(new Position(17, 1), new DoubleValue(new Position(17, 85), 100.4), new DoubleValue(new Position(17, 90), 15.43));
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new VoidFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new PointParameter(new Position(15, 15), "m"), point),
                    new ObjectAccess(new Position(20, 1), new Identifier(new Position(20, 1), "m"), new FunctionCall(new Position(20, 3), new Identifier(new Position(20, 1), "print")))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals("Point(100.4, 15.43)", outStream.toString());
    }

    @Test
    void givenSceneValue_whenPrintingEmptyScene_thenValueIsPrintedWithoutItsPosition() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new VoidFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new SceneParameter(new Position(15, 15), "m"), new SceneValue(new Position(15, 20))),
                    new ObjectAccess(new Position(20, 1), new Identifier(new Position(20, 1), "m"), new FunctionCall(new Position(20, 3), new Identifier(new Position(20, 1), "print")))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals("Scene[]", outStream.toString());
    }

    @Test
    void givenSceneValue_whenPrintingSceneWithSectionAdded_thenValueIsPrintedWithoutItsPosition() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        SectionValue section = new SectionValue(new Position(17, 80), new PointValue(new Position(17, 1), new DoubleValue(new Position(17, 85), 100.4), new DoubleValue(new Position(17, 90), 1.4)), new PointValue(new Position(17, 100), new DoubleValue(new Position(17, 110), 22.3), new DoubleValue(new Position(17, 115), 15.0)));
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new VoidFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new SceneParameter(new Position(15, 15), "m"), new SceneValue(new Position(15, 20))),
                    new ObjectAccess(new Position(17, 1), new Identifier(new Position(17, 1), "m"), new FunctionCall(new Position(17, 3), new Identifier(new Position(17, 1), "add"), section)),
                    new ObjectAccess(new Position(20, 1), new Identifier(new Position(20, 1), "m"), new FunctionCall(new Position(20, 3), new Identifier(new Position(20, 1), "print")))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals("Scene[Section[Point(100.4, 1.4), Point(22.3, 15.0)]]", outStream.toString());
    }

    @Test
    void givenSceneValue_whenPrintingSceneWithTwoSectionsAdded_thenValueIsPrintedWithoutItsPosition() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        SectionValue section = new SectionValue(new Position(17, 80), new PointValue(new Position(17, 1), new DoubleValue(new Position(17, 85), 100.4), new DoubleValue(new Position(17, 90), 1.4)), new PointValue(new Position(17, 100), new DoubleValue(new Position(17, 110), 22.3), new DoubleValue(new Position(17, 115), 15.0)));
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new VoidFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new SceneParameter(new Position(15, 15), "m"), new SceneValue(new Position(15, 20))),
                    new ObjectAccess(new Position(17, 1), new Identifier(new Position(17, 1), "m"), new FunctionCall(new Position(17, 3), new Identifier(new Position(17, 1), "add"), section)),
                    new ObjectAccess(new Position(17, 1), new Identifier(new Position(17, 1), "m"), new FunctionCall(new Position(17, 3), new Identifier(new Position(17, 1), "add"), section)),
                    new ObjectAccess(new Position(20, 1), new Identifier(new Position(20, 1), "m"), new FunctionCall(new Position(20, 3), new Identifier(new Position(20, 1), "print")))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals("Scene[Section[Point(100.4, 1.4), Point(22.3, 15.0)], Section[Point(100.4, 1.4), Point(22.3, 15.0)]]", outStream.toString());
    }

    @Test
    void givenSectionValue_whenPrintingSectionWithSectionAdded_thenValueIsPrintedWithoutItsPosition() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        SectionValue section = new SectionValue(new Position(17, 80), new PointValue(new Position(17, 1), new DoubleValue(new Position(17, 85), 100.4), new DoubleValue(new Position(17, 90), 1.4)), new PointValue(new Position(17, 100), new DoubleValue(new Position(17, 110), 22.3), new DoubleValue(new Position(17, 115), 15.0)));
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new VoidFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new SectionParameter(new Position(15, 15), "m"), section),
                    new ObjectAccess(new Position(20, 1), new Identifier(new Position(20, 1), "m"), new FunctionCall(new Position(20, 3), new Identifier(new Position(20, 1), "print")))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals("Section[Point(100.4, 1.4), Point(22.3, 15.0)]", outStream.toString());
    }

    @Test
    void givenStringValue_whenPrintingStringValue_thenValueIsPrintedWithoutItsPosition() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new VoidFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new StringParameter(new Position(15, 15), "m"), new StringValue(new Position(15, 20), "true")),
                    new ObjectAccess(new Position(20, 1), new Identifier(new Position(20, 1), "m"), new FunctionCall(new Position(20, 3), new Identifier(new Position(20, 1), "print")))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals("true", outStream.toString());
    }
}
