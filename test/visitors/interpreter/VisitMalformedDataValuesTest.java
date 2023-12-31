package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IErrorHandler;
import parser.IFunctionDef;
import parser.program_components.*;
import parser.program_components.data_values.*;
import parser.program_components.function_definitions.SceneFunctionDef;
import parser.program_components.function_definitions.SectionFunctionDef;
import parser.program_components.parameters.FigureParameter;
import parser.program_components.parameters.PointParameter;
import parser.program_components.parameters.SceneParameter;
import parser.program_components.parameters.SectionParameter;
import parser.program_components.statements.AssignmentStatement;
import visitors.ContextManager;
import visitors.Interpreter;
import visitors.exceptions.IdentifierNotFoundException;
import visitors.exceptions.IncompatibleDataTypeException;
import visitors.exceptions.IncompatibleMethodArgumentException;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitMalformedDataValuesTest {
    private static void assertErrorLogs(IErrorHandler errorHandler, Interpreter interpreter, Program program, List<Exception> expectedErrorLog) {
        boolean wasExceptionCaught = false;
        try {
            program.accept(interpreter);
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
    void givenEmptyFigure_whenTryingToAddSthDifferentThanSection_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        FigureValue figure = new FigureValue(new Position(65, 10));
        PointValue point = new PointValue(new Position(65, 10), new IntValue(new Position(65, 15), 5), new IntValue(new Position(65, 20), 51));
        functions.put("main", new SectionFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new FigureParameter(new Position(60, 1), "myList"), figure),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "add"), point))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(figure, point)
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenEmptyFigure_whenTryingToAddTwoSectionsThatAreNotConnected_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        FigureValue figure = new FigureValue(new Position(65, 10));
        SectionValue firstSection = new SectionValue(new Position(65, 10), new PointValue(new Position(65, 15), new IntValue(new Position(65, 15), 5), new IntValue(new Position(65, 20), 51)), new PointValue(new Position(65, 35), new IntValue(new Position(65, 35), 5), new IntValue(new Position(65, 40), 51)));
        SectionValue secondSection = new SectionValue(new Position(75, 10), new PointValue(new Position(75, 15), new IntValue(new Position(75, 15), 15), new IntValue(new Position(75, 20), 51)), new PointValue(new Position(75, 35), new IntValue(new Position(75, 35), 7), new IntValue(new Position(75, 40), 251)));
        functions.put("main", new SectionFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new FigureParameter(new Position(60, 1), "myList"), figure),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "add"), firstSection)),
                new ObjectAccess(new Position(75, 1), new Identifier(new Position(75, 1), "myList"), new FunctionCall(new Position(75, 8), new Identifier(new Position(75, 8), "add"), secondSection))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleMethodArgumentException(figure, new IntValue(new Position(75, 10), 5))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenScene_whenTryingToAddBoolValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        SceneValue scene = new SceneValue(new Position(65, 10));
        BoolValue incompatibleValue = new BoolValue(new Position(65, 10), true);
        functions.put("main", new SceneFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new SceneParameter(new Position(60, 1), "myVar"), scene),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myVar"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "add"), incompatibleValue))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(scene, incompatibleValue)
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenPointDeclaration_whenFirstPointParamIsNotIntOrDoubleValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        functions.put("main", new SceneFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new PointParameter(new Position(60, 1), "myVar"), new PointValue(new Position(60, 10), new BoolValue(new Position(60, 10), true), new BoolValue(new Position(60, 16), true)))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new DoubleValue(new Position(60, 10), null), new BoolValue(new Position(60, 10), true))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenPointDeclaration_whenSecondPointParamIsNotIntOrDoubleValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        functions.put("main", new SceneFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new PointParameter(new Position(60, 1), "myVar"), new PointValue(new Position(60, 10), new IntValue(new Position(60, 15), 5), new BoolValue(new Position(60, 20), true)))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new DoubleValue(new Position(60, 20), null), new BoolValue(new Position(60, 20), true))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenPointDeclaration_whenFirstPointParamIsUnknownIdentifier_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        functions.put("main", new SceneFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new PointParameter(new Position(60, 1), "myVar"), new PointValue(new Position(60, 10), new Identifier(new Position(60, 10), "aSomeVar"), new BoolValue(new Position(60, 26), true)))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IdentifierNotFoundException(new Identifier(new Position(60, 10), "aSomeVar"))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenPointDeclaration_whenSecondPointParamIsUnknownIdentifier_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        functions.put("main", new SceneFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new PointParameter(new Position(60, 1), "myVar"), new PointValue(new Position(60, 10), new IntValue(new Position(60, 15), 5), new Identifier(new Position(60, 20), "aTrue")))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IdentifierNotFoundException(new Identifier(new Position(60, 20), "aTrue"))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenSectionDeclaration_whenFirstParamIsNotPointValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        PointValue secondPoint = new PointValue(new Position(66, 10), new IntValue(new Position(66, 15), 5), new IntValue(new Position(66, 20), 51));
        functions.put("main", new SceneFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new SectionParameter(new Position(60, 1), "myVar"), new SectionValue(new Position(60, 10), new IntValue(new Position(60, 15), 5), secondPoint))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new PointValue(new Position(60, 15), null, null), new IntValue(new Position(60, 15), 5))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenSectionDeclaration_whenSecondParamIsNotPointValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        PointValue firstPoint = new PointValue(new Position(65, 10), new IntValue(new Position(65, 15), 5), new IntValue(new Position(65, 20), 51));
        functions.put("main", new SceneFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new SectionParameter(new Position(60, 1), "myVar"), new SectionValue(new Position(60, 10), firstPoint, new BoolValue(new Position(62, 62), false)))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new PointValue(new Position(62, 62), null, null), new BoolValue(new Position(62, 62), false))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenSectionDeclaration_whenFirstParamIsUnknownIdentifier_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        PointValue firstPoint = new PointValue(new Position(65, 10), new IntValue(new Position(65, 15), 5), new IntValue(new Position(65, 20), 51));
        functions.put("main", new SceneFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new SectionParameter(new Position(60, 1), "myVar"), new SectionValue(new Position(60, 10), firstPoint, new Identifier(new Position(62, 62), "falseIdent")))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IdentifierNotFoundException(new Identifier(new Position(62, 62), "falseIdent"))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenSectionDeclaration_whenSecondParamIsUnknownIdentifier_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        PointValue secondPoint = new PointValue(new Position(66, 10), new IntValue(new Position(66, 15), 5), new IntValue(new Position(66, 20), 51));
        functions.put("main", new SceneFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new SectionParameter(new Position(60, 1), "myVar"), new SectionValue(new Position(60, 10), new Identifier(new Position(60, 15), "a5"), secondPoint))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IdentifierNotFoundException(new Identifier(new Position(60, 15), "a5"))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }
}
