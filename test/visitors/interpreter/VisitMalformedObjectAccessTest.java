package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IErrorHandler;
import parser.IExpression;
import parser.IFunctionDef;
import parser.program_components.*;
import parser.program_components.data_values.DoubleValue;
import parser.program_components.data_values.FigureValue;
import parser.program_components.data_values.IntValue;
import parser.program_components.data_values.lists.*;
import parser.program_components.function_definitions.FigureFunctionDef;
import parser.program_components.function_definitions.IntFunctionDef;
import parser.program_components.parameters.*;
import parser.program_components.statements.AssignmentStatement;
import parser.program_components.statements.ReturnStatement;
import visitors.ContextManager;
import visitors.Interpreter;
import visitors.exceptions.IdentifierNotFoundException;
import visitors.exceptions.IncompatibleDataTypeException;
import visitors.exceptions.UndefinedFunctionCallException;
import visitors.exceptions.UndefinedMethodCallException;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitMalformedObjectAccessTest {
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
    void givenObjectAccessStmntWithIntList_whenFunctionNotImplemented_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        functions.put("main", new IntFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new IntListParameter(new Position(60, 1), "myList"), new IntListValue(new Position(60, 10))),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "put"), new IntValue(new Position(65, 10), 57)))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new UndefinedFunctionCallException(new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "put"), new IntValue(new Position(65, 10), 57)))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenObjectAccessStmntWithIntList_whenRightExpIsNotFunctionCall_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        functions.put("main", new IntFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new IntListParameter(new Position(60, 1), "myList"), new IntListValue(new Position(60, 10))),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new Identifier(new Position(65, 8), "put"))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new UndefinedMethodCallException(new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new Identifier(new Position(65, 8), "put")))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenObjectAccessStmntWithIntList_whenTryingToAddDoubleValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        functions.put("main", new IntFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new IntListParameter(new Position(60, 1), "myList"), new IntListValue(new Position(60, 10))),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "add"), new DoubleValue(new Position(65, 13), 3.1)))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new IntListValue(new Position(60, 10)), new DoubleValue(new Position(65, 13), 3.1))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenObjectAccessStmntWithDoubleList_whenTryingToAddIntValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        functions.put("main", new IntFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new DoubleListParameter(new Position(60, 1), "myList"), new DoubleListValue(new Position(60, 10))),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "add"), new IntValue(new Position(65, 13), 3)))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new DoubleListValue(new Position(60, 10)), new IntValue(new Position(65, 13), 3))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenObjectAccessStmntWithBoolList_whenTryingToAddIntValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        functions.put("main", new IntFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new BoolListParameter(new Position(60, 1), "myList"), new BoolListValue(new Position(60, 10))),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "add"), new IntValue(new Position(65, 13), 3)))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new BoolListValue(new Position(60, 10)), new IntValue(new Position(65, 13), 3))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenObjectAccessStmntWithStringList_whenTryingToAddIntValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        functions.put("main", new IntFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new StringListParameter(new Position(60, 1), "myList"), new StringListValue(new Position(60, 10))),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "add"), new IntValue(new Position(65, 13), 3)))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new StringListValue(new Position(60, 10)), new IntValue(new Position(65, 13), 3))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenObjectAccessStmntWithFigureList_whenTryingToAddIntValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        functions.put("main", new IntFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new FigureListParameter(new Position(60, 1), "myList"), new FigureListValue(new Position(60, 10))),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "add"), new IntValue(new Position(65, 13), 3)))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new FigureListValue(new Position(60, 10)), new IntValue(new Position(65, 13), 3))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenObjectAccessStmntWithPointList_whenTryingToAddIntValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        functions.put("main", new IntFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new PointListParameter(new Position(60, 1), "myList"), new PointListValue(new Position(60, 10))),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "add"), new IntValue(new Position(65, 13), 3)))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new PointListValue(new Position(60, 10)), new IntValue(new Position(65, 13), 3))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenObjectAccessStmntWithSectionList_whenTryingToAddIntValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        functions.put("main", new IntFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new SectionListParameter(new Position(60, 1), "myList"), new SectionListValue(new Position(60, 10))),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "add"), new IntValue(new Position(65, 13), 3)))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new SectionListValue(new Position(60, 10)), new IntValue(new Position(65, 13), 3))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenObjectAccessStmntWithSceneList_whenTryingToAddIntValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        functions.put("main", new IntFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new SceneListParameter(new Position(60, 1), "myList"), new SceneListValue(new Position(60, 10))),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "add"), new IntValue(new Position(65, 13), 3)))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new SceneListValue(new Position(60, 10)), new IntValue(new Position(65, 13), 3))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenObjectAccessStmntWithScene_whenAddingValueFromUnknownIdentifier_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        FigureValue figure = new FigureValue(new Position(50, 10));
        functions.put("main", new FigureFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new SceneListParameter(new Position(60, 1), "myList"), new Identifier(new Position(60, 10), "fig")),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "add"), figure)),
                new ReturnStatement(new Position(70, 1),
                        new ObjectAccess(new Position(75, 1), new Identifier(new Position(75, 1), "myList"), new FunctionCall(new Position(75, 8), new Identifier(new Position(75, 8), "get"), new IntValue(new Position(75, 10), 0))))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IdentifierNotFoundException(new Identifier(new Position(60, 10), "fig"))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenObjectAccessStmnt_whenTryingToCallAddWithoutArgs_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        functions.put("main", new FigureFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new SceneListParameter(new Position(60, 1), "myList"), new SceneListValue(new Position(60, 10))),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "add")))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new UndefinedFunctionCallException(new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "add")))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenObjectAccessStmnt_whenTryingToCallAddWithTwoArgs_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        ArrayList<IExpression> args = new ArrayList<>();
        args.add(new IntValue(new Position(65, 18), 11));
        args.add(new IntValue(new Position(65, 25), 11));
        functions.put("main", new FigureFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new SceneListParameter(new Position(60, 1), "myList"), new SceneListValue(new Position(60, 10))),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "add"), args))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new UndefinedFunctionCallException(new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "add")))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenObjectAccessStmnt_whenTryingToCallGetWithoutArgs_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        functions.put("main", new FigureFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new SceneListParameter(new Position(60, 1), "myList"), new SceneListValue(new Position(60, 10))),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "get")))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new UndefinedFunctionCallException(new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "get")))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenObjectAccessStmnt_whenTryingToCallGetWithTwoArgs_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        ArrayList<IExpression> args = new ArrayList<>();
        args.add(new IntValue(new Position(65, 18), 11));
        args.add(new IntValue(new Position(65, 25), 11));
        functions.put("main", new FigureFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new SceneListParameter(new Position(60, 1), "myList"), new SceneListValue(new Position(60, 10))),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "get"), args))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new UndefinedFunctionCallException(new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "get")))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }
}
