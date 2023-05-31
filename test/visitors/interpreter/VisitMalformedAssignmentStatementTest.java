package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IFunctionDef;
import parser.program_components.CodeBlock;
import parser.program_components.Program;
import parser.program_components.data_values.BoolValue;
import parser.program_components.data_values.DoubleValue;
import parser.program_components.data_values.IntValue;
import parser.program_components.data_values.StringValue;
import parser.program_components.data_values.lists.BoolListValue;
import parser.program_components.data_values.lists.StringListValue;
import parser.program_components.function_definitions.IntFunctionDef;
import parser.program_components.parameters.*;
import parser.program_components.statements.AssignmentStatement;
import visitors.Interpreter;
import visitors.exceptions.IncompatibleDataTypeException;
import visitors.exceptions.ParameterNotFoundException;
import visitors.utils.MockedContextManager;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitMalformedAssignmentStatementTest {
    private static void assertErrorLogs(MockedExitInterpreterErrorHandler errorHandler, Interpreter interpreter, Program program, List<Exception> expectedErrorLog) {
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
    void givenAssignmentStmnt_whenIntParamAndStringValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new IntParameter(new Position(15, 15), "m"), new StringValue(new Position(15, 20), "2"))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new IntValue(new Position(15, 20), null), new StringValue(new Position(15, 20), "2"))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenAssignmentStmnt_whenDoubleParamAndStringValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new DoubleParameter(new Position(15, 15), "m"), new StringValue(new Position(15, 20), "2.01"))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new DoubleValue(new Position(15, 20), null), new StringValue(new Position(15, 20), "2"))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenAssignmentStmnt_whenStringParamAndDoubleValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new StringParameter(new Position(15, 15), "m"), new DoubleValue(new Position(15, 20), 2.01))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new StringParameter(new Position(15, 15), "m"), new DoubleValue(new Position(15, 20), 2.01))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenAssignmentStmnt_whenBoolParamAndStringValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new BoolParameter(new Position(15, 15), "m"), new StringValue(new Position(15, 20), "true"))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new BoolParameter(new Position(15, 15), "m"), new StringValue(new Position(15, 20), "true"))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenAssignmentStmnt_whenPointParamAndStringValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new PointParameter(new Position(15, 15), "m"), new StringValue(new Position(15, 20), "true"))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new PointParameter(new Position(15, 15), "m"), new StringValue(new Position(15, 20), "true"))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenAssignmentStmnt_whenSectionParamAndStringValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new SectionParameter(new Position(15, 15), "m"), new StringValue(new Position(15, 20), "true"))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new SectionParameter(new Position(15, 15), "m"), new StringValue(new Position(15, 20), "true"))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenAssignmentStmnt_whenFigureAndBoolValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new FigureParameter(new Position(15, 15), "m"), new BoolValue(new Position(15, 20), true))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new FigureParameter(new Position(15, 15), "m"), new BoolValue(new Position(15, 20), true))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenAssignmentStmnt_whenSceneAndBoolValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new SceneParameter(new Position(15, 15), "m"), new BoolValue(new Position(15, 20), true))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new SceneParameter(new Position(15, 15), "m"), new BoolValue(new Position(15, 20), true))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenAssignmentStmnt_whenIntListParamAndStringListValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new IntListParameter(new Position(15, 15), "m"), new StringListValue(new Position(15, 20)))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new IntListParameter(new Position(15, 15), "m"), new StringListValue(new Position(15, 20)))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenAssignmentStmnt_whenDoubleListParamAndStringListValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new DoubleListParameter(new Position(15, 15), "m"), new StringListValue(new Position(15, 20)))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new DoubleListParameter(new Position(15, 15), "m"), new StringListValue(new Position(15, 20)))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenAssignmentStmnt_whenStringListParamAndBoolListValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new StringListParameter(new Position(15, 15), "m"), new BoolListValue(new Position(15, 20)))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new StringListParameter(new Position(15, 15), "m"), new BoolListValue(new Position(15, 20)))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenAssignmentStmnt_whenBoolListParamAndStringListValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new BoolListParameter(new Position(15, 15), "m"), new StringListValue(new Position(15, 20)))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new BoolListParameter(new Position(15, 15), "m"), new StringListValue(new Position(15, 20)))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenAssignmentStmnt_whenPointListParamAndStringListValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new PointListParameter(new Position(15, 15), "m"), new StringListValue(new Position(15, 20)))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new PointListParameter(new Position(15, 15), "m"), new StringListValue(new Position(15, 20)))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenAssignmentStmnt_whenSectionListParamAndStringListValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new SectionListParameter(new Position(15, 15), "m"), new StringListValue(new Position(15, 20)))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new SectionListParameter(new Position(15, 15), "m"), new StringListValue(new Position(15, 20)))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenAssignmentStmnt_whenFigureListParamAndStringListValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new FigureListParameter(new Position(15, 15), "m"), new StringListValue(new Position(15, 20)))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new FigureListParameter(new Position(15, 15), "m"), new StringListValue(new Position(15, 20)))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenAssignmentStmnt_whenSceneListParamAndStringListValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new SceneListParameter(new Position(15, 15), "m"), new StringListValue(new Position(15, 20)))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new SceneListParameter(new Position(15, 15), "m"), new StringListValue(new Position(15, 20)))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenReassignedParam_whenParamNameNotPresentInContext_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new ReassignedParameter(new Position(15, 15), "m"), new StringListValue(new Position(15, 20)))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new ParameterNotFoundException(new ReassignedParameter(new Position(15, 15), "m"), new StringListValue(new Position(15, 20)))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenIntParam_whenReassigningStringListValue_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new IntParameter(new Position(15, 15), "m"), new IntValue(new Position(15, 20), 2)),
                    new AssignmentStatement(new Position(18, 15), new ReassignedParameter(new Position(18, 15), "m"), new StringListValue(new Position(18, 20)))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleDataTypeException(new IntValue(new Position(18, 20), 2), new StringListValue(new Position(18, 20)))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }
}
