package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.*;
import parser.program_components.*;
import parser.program_components.data_values.BoolValue;
import parser.program_components.data_values.DoubleValue;
import parser.program_components.data_values.IntValue;
import parser.program_components.data_values.PointValue;
import parser.program_components.expressions.MultiplicationExpression;
import parser.program_components.function_definitions.IntFunctionDef;
import parser.program_components.parameters.IntParameter;
import parser.program_components.parameters.PointParameter;
import parser.program_components.statements.AssignmentStatement;
import parser.program_components.statements.ReturnStatement;
import visitors.ContextManager;
import visitors.Interpreter;
import visitors.exceptions.*;
import visitors.utils.MockedContextManager;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitMalformedFunctionCallTest {
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
    void givenProgramWithOnlyMainFunc_whenFunctionCallDetectedAndFuncDefinitionNotKnown_thenErrorLogRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new FunctionCall(new Position(10, 10), new Identifier(new Position(10, 10), "mySqrt")),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new UndefinedFunctionCallException(new FunctionCall(new Position(10, 10), new Identifier(new Position(10, 10), "mySqrt")))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenUserDeclaredFunctionCallTakesOneArg_whenArgTypeIsNotValid_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        LinkedHashMap<String, IParameter> params = new LinkedHashMap<>();
        params.put("a", new IntParameter(new Position(5, 5), "a"));
        functions.put("main", new IntFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new ReturnStatement(new Position(70, 1), new FunctionCall(new Position(70, 10), new Identifier(new Position(60, 10), "getTwice"), new BoolValue(new Position(60, 20), true)))
        ))));
        functions.put("getTwice", new IntFunctionDef(new Position(1, 1), "getTwice", params, new CodeBlock(new Position(10, 10), List.of(
                new ReturnStatement(new Position(30, 30), new MultiplicationExpression(new Position(30, 40), new Identifier(new Position(30, 40), "a"), new IntValue(new Position(30, 45), 2))))
        )));
        Program program = new Program(new Position(1, 1), functions);
        ArrayList<IVisitable> expectedArgs = new ArrayList<>();
        expectedArgs.add(new IntParameter(new Position(5, 5), "a"));
        ArrayList<IVisitable> receivedArgs = new ArrayList<>();
        receivedArgs.add(new BoolValue(new Position(60, 20), true));
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleArgumentsListException(new FunctionCall(new Position(70, 10), new Identifier(new Position(60, 10), "getTwice"), new BoolValue(new Position(60, 20), true)), expectedArgs, receivedArgs)
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenUserDeclaredFunction_whenItConstantlyCallsItself_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        functions.put("main", new IntFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new ReturnStatement(new Position(70, 1), new FunctionCall(new Position(70, 10), new Identifier(new Position(60, 10), "getTwice")))
        ))));
        functions.put("getTwice", new IntFunctionDef(new Position(1, 1), "getTwice", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                new ReturnStatement(new Position(30, 30), new FunctionCall(new Position(30, 40), new Identifier(new Position(30, 40), "getTwice")))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new ExceededMaxRecursionDepthException(new FunctionCall(new Position(30, 40), new Identifier(new Position(30, 40), "getTwice")), 10)
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenTwoUserDeclaredFunction_whenTheseTwoFunctionsCallOneAnotherAndExceedFunctionStack_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        functions.put("main", new IntFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new ReturnStatement(new Position(70, 1), new FunctionCall(new Position(70, 10), new Identifier(new Position(60, 10), "getTwice")))
        ))));
        functions.put("getTwice", new IntFunctionDef(new Position(1, 1), "getTwice", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                new ReturnStatement(new Position(30, 30), new FunctionCall(new Position(30, 40), new Identifier(new Position(30, 40), "getTriple")))
        ))));
        functions.put("getTriple", new IntFunctionDef(new Position(1, 1), "getTriple", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                new ReturnStatement(new Position(30, 30), new FunctionCall(new Position(30, 40), new Identifier(new Position(30, 40), "getTwice")))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new ExceededFunctionCallStackSizeException(new FunctionCall(new Position(30, 40), new Identifier(new Position(30, 40), "getTwice")), 100)
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenPointValue_whenSettingRColorValueBelowZero_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        ArrayList<IExpression> params = new ArrayList<>();
        params.add(new IntValue(new Position(15, 20), -1));
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new PointParameter(new Position(15, 15), "m"), new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new DoubleValue(new Position(15, 25), 11.0))),
                    new ObjectAccess(new Position(20, 1), new Identifier(new Position(20, 1), "m"), new FunctionCall(new Position(20, 3), new Identifier(new Position(20, 3), "setRColor"), params)),
                    new ReturnStatement(new Position(22, 1), new ObjectAccess(new Position(23, 1), new Identifier(new Position(23, 1), "m"), new FunctionCall(new Position(23, 3), new Identifier(new Position(23, 3), "getRColor"))))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleMethodArgumentException(new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new DoubleValue(new Position(15, 25), 11.0)), new IntValue(new Position(15, 20), -1))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenPointValue_whenSettingGColorValueBelowZero_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        ArrayList<IExpression> params = new ArrayList<>();
        params.add(new IntValue(new Position(15, 20), -1));
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new PointParameter(new Position(15, 15), "m"), new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new DoubleValue(new Position(15, 25), 11.0))),
                    new ObjectAccess(new Position(20, 1), new Identifier(new Position(20, 1), "m"), new FunctionCall(new Position(20, 3), new Identifier(new Position(20, 3), "setGColor"), params)),
                    new ReturnStatement(new Position(22, 1), new ObjectAccess(new Position(23, 1), new Identifier(new Position(23, 1), "m"), new FunctionCall(new Position(23, 3), new Identifier(new Position(23, 3), "getGColor"))))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleMethodArgumentException(new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new DoubleValue(new Position(15, 25), 11.0)), new IntValue(new Position(15, 20), -1))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenPointValue_whenSettingBColorValueBelowZero_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        ArrayList<IExpression> params = new ArrayList<>();
        params.add(new IntValue(new Position(15, 20), -1));
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new PointParameter(new Position(15, 15), "m"), new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new DoubleValue(new Position(15, 25), 11.0))),
                    new ObjectAccess(new Position(20, 1), new Identifier(new Position(20, 1), "m"), new FunctionCall(new Position(20, 3), new Identifier(new Position(20, 3), "setBColor"), params)),
                    new ReturnStatement(new Position(22, 1), new ObjectAccess(new Position(23, 1), new Identifier(new Position(23, 1), "m"), new FunctionCall(new Position(23, 3), new Identifier(new Position(23, 3), "getBColor"))))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleMethodArgumentException(new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new DoubleValue(new Position(15, 25), 11.0)), new IntValue(new Position(15, 20), -1))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenPointValue_whenSettingRColorValueAboveLimit_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        ArrayList<IExpression> params = new ArrayList<>();
        params.add(new IntValue(new Position(15, 20), 256));
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new PointParameter(new Position(15, 15), "m"), new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new DoubleValue(new Position(15, 25), 11.0))),
                    new ObjectAccess(new Position(20, 1), new Identifier(new Position(20, 1), "m"), new FunctionCall(new Position(20, 3), new Identifier(new Position(20, 3), "setRColor"), params)),
                    new ReturnStatement(new Position(22, 1), new ObjectAccess(new Position(23, 1), new Identifier(new Position(23, 1), "m"), new FunctionCall(new Position(23, 3), new Identifier(new Position(23, 3), "getRColor"))))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleMethodArgumentException(new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new DoubleValue(new Position(15, 25), 11.0)), new IntValue(new Position(15, 20), 256))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenPointValue_whenSettingGColorValueAboveLimit_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        ArrayList<IExpression> params = new ArrayList<>();
        params.add(new IntValue(new Position(15, 20), 256));
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new PointParameter(new Position(15, 15), "m"), new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new DoubleValue(new Position(15, 25), 11.0))),
                    new ObjectAccess(new Position(20, 1), new Identifier(new Position(20, 1), "m"), new FunctionCall(new Position(20, 3), new Identifier(new Position(20, 3), "setGColor"), params)),
                    new ReturnStatement(new Position(22, 1), new ObjectAccess(new Position(23, 1), new Identifier(new Position(23, 1), "m"), new FunctionCall(new Position(23, 3), new Identifier(new Position(23, 3), "getGColor"))))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleMethodArgumentException(new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new DoubleValue(new Position(15, 25), 11.0)), new IntValue(new Position(15, 20), 256))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }

    @Test
    void givenPointValue_whenSettingBColorValueAboveLimit_thenErrorIsRegistered() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        ArrayList<IExpression> params = new ArrayList<>();
        params.add(new IntValue(new Position(15, 20), 256));
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new PointParameter(new Position(15, 15), "m"), new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new DoubleValue(new Position(15, 25), 11.0))),
                    new ObjectAccess(new Position(20, 1), new Identifier(new Position(20, 1), "m"), new FunctionCall(new Position(20, 3), new Identifier(new Position(20, 3), "setBColor"), params)),
                    new ReturnStatement(new Position(22, 1), new ObjectAccess(new Position(23, 1), new Identifier(new Position(23, 1), "m"), new FunctionCall(new Position(23, 3), new Identifier(new Position(23, 3), "getBColor"))))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        List<Exception> expectedErrorLog = List.of(
                new IncompatibleMethodArgumentException(new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new DoubleValue(new Position(15, 25), 11.0)), new IntValue(new Position(15, 20), 256))
        );

        assertErrorLogs(errorHandler, interpreter, program, expectedErrorLog);
    }
}
