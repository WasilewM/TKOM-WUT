package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IFunctionDef;
import parser.program_components.*;
import parser.program_components.data_values.*;
import parser.program_components.function_definitions.FigureFunctionDef;
import parser.program_components.function_definitions.SceneFunctionDef;
import parser.program_components.function_definitions.SectionFunctionDef;
import parser.program_components.parameters.FigureParameter;
import parser.program_components.parameters.PointParameter;
import parser.program_components.parameters.SceneParameter;
import parser.program_components.parameters.SectionParameter;
import parser.program_components.statements.AssignmentStatement;
import parser.program_components.statements.ReturnStatement;
import visitors.ContextManager;
import visitors.Interpreter;
import visitors.exceptions.IncompatibleDataTypeException;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitDataValuesTest {
    @Test
    void givenEmptyFigure_whenTryingToAddSection_thenExecuteAddMethod() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        FigureValue figureValue = new FigureValue(new Position(65, 10));
        SectionValue expectedLastResult = new SectionValue(new Position(65, 10), new PointValue(new Position(65, 15), new IntValue(new Position(65, 15), 5), new IntValue(new Position(65, 20), 51)), new PointValue(new Position(65, 35), new IntValue(new Position(65, 35), 5), new IntValue(new Position(65, 40), 51)));
        functions.put("main", new SectionFunctionDef(new Position(50, 1), "main", new HashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new FigureParameter(new Position(60, 1), "myList"), figureValue),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "add"), expectedLastResult)),
                new ReturnStatement(new Position(70, 1),
                        new ObjectAccess(new Position(75, 1), new Identifier(new Position(75, 1), "myList"), new FunctionCall(new Position(75, 8), new Identifier(new Position(75, 8), "get"), new IntValue(new Position(75, 10), 0))))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);
        figureValue.add(expectedLastResult);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenEmptyFigure_whenTryingToAddTwoConnectedSections_thenExecuteAddMethod() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        FigureValue figureValue = new FigureValue(new Position(65, 10));
        SectionValue firstSection = new SectionValue(new Position(65, 10), new PointValue(new Position(65, 15), new IntValue(new Position(65, 15), 5), new IntValue(new Position(65, 20), 51)), new PointValue(new Position(65, 35), new IntValue(new Position(65, 35), 5), new IntValue(new Position(65, 40), 51)));
        SectionValue secondSection = new SectionValue(new Position(75, 10), new PointValue(new Position(75, 15), new IntValue(new Position(75, 15), 5), new IntValue(new Position(75, 20), 51)), new PointValue(new Position(75, 35), new IntValue(new Position(75, 35), 7), new IntValue(new Position(75, 40), 251)));
        functions.put("main", new FigureFunctionDef(new Position(50, 1), "main", new HashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new FigureParameter(new Position(60, 1), "myList"), figureValue),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "add"), firstSection)),
                new ObjectAccess(new Position(75, 1), new Identifier(new Position(75, 1), "myList"), new FunctionCall(new Position(75, 8), new Identifier(new Position(75, 8), "add"), secondSection)),
                new ReturnStatement(new Position(70, 1), new Identifier(new Position(75, 1), "myList"))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);
        figureValue.add(firstSection);
        figureValue.add(secondSection);

        assertEquals(figureValue, interpreter.getLastResult());
    }

    @Test
    void givenEmptyScene_whenTryingToAddFigure_thenExecuteAddMethod() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        SceneValue scene = new SceneValue(new Position(65, 10));
        FigureValue figure = new FigureValue(new Position(65, 10));
        functions.put("main", new SceneFunctionDef(new Position(50, 1), "main", new HashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new SceneParameter(new Position(60, 1), "myVar"), scene),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myVar"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "add"), figure)),
                new ReturnStatement(new Position(70, 1), new Identifier(new Position(70, 10), "myVar"))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);
        try {
            scene.add(figure);
        } catch (IncompatibleDataTypeException e) {
            assert false;
        }

        assertEquals(scene, interpreter.getLastResult());
    }

    @Test
    void givenEmptyScene_whenTryingToAddPoint_thenExecuteAddMethod() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        SceneValue scene = new SceneValue(new Position(65, 10));
        PointValue point = new PointValue(new Position(65, 10), new IntValue(new Position(65, 15), 5), new IntValue(new Position(65, 20), 51));
        functions.put("main", new SceneFunctionDef(new Position(50, 1), "main", new HashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new SceneParameter(new Position(60, 1), "myVar"), scene),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myVar"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "add"), point)),
                new ReturnStatement(new Position(70, 1), new Identifier(new Position(70, 10), "myVar"))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);
        try {
            scene.add(point);
        } catch (IncompatibleDataTypeException e) {
            assert false;
        }

        assertEquals(scene, interpreter.getLastResult());
    }

    @Test
    void givenEmptyScene_whenTryingToAddSection_thenExecuteAddMethod() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        SceneValue scene = new SceneValue(new Position(65, 10));
        SectionValue section = new SectionValue(new Position(65, 10), new PointValue(new Position(65, 15), new IntValue(new Position(65, 15), 5), new IntValue(new Position(65, 20), 51)), new PointValue(new Position(65, 35), new IntValue(new Position(65, 35), 5), new IntValue(new Position(65, 40), 51)));
        functions.put("main", new SceneFunctionDef(new Position(50, 1), "main", new HashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new SceneParameter(new Position(60, 1), "myVar"), scene),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myVar"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "add"), section)),
                new ReturnStatement(new Position(70, 1), new Identifier(new Position(70, 10), "myVar"))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);
        try {
            scene.add(section);
        } catch (IncompatibleDataTypeException e) {
            assert false;
        }

        assertEquals(scene, interpreter.getLastResult());
    }

    @Test
    void givenSectionDeclaration_whenFirstParamIsUnknownIdentifier_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        PointValue firstPoint = new PointValue(new Position(15, 50), new DoubleValue(new Position(15, 15), 5.0), new DoubleValue(new Position(15, 20), 51.8));
        PointValue secondPoint = new PointValue(new Position(16, 10), new DoubleValue(new Position(16, 15), 5.0), new DoubleValue(new Position(16, 20), 51.9));
        SectionValue expectedSection = new SectionValue(new Position(60, 10), firstPoint, secondPoint);
        functions.put("main", new SectionFunctionDef(new Position(10, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                new AssignmentStatement(new Position(new Position(15, 10)), new PointParameter(new Position(15, 30), "a"), firstPoint),
                new AssignmentStatement(new Position(new Position(15, 10)), new PointParameter(new Position(16, 30), "b"), secondPoint),
                new AssignmentStatement(new Position(60, 1), new SectionParameter(new Position(60, 1), "myVar"), new SectionValue(new Position(60, 10), new Identifier(new Position(61, 61), "a"), new Identifier(new Position(62, 62), "b"))),
                new ReturnStatement(new Position(70, 1), new Identifier(new Position(70, 10), "myVar"))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedSection, interpreter.getLastResult());
    }
}
