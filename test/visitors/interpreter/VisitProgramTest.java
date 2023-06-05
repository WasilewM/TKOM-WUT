package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IFunctionDef;
import parser.IParameter;
import parser.program_components.*;
import parser.program_components.data_values.*;
import parser.program_components.function_definitions.IntFunctionDef;
import parser.program_components.function_definitions.VoidFunctionDef;
import parser.program_components.parameters.IntParameter;
import parser.program_components.parameters.SceneParameter;
import parser.program_components.statements.*;
import visitors.Context;
import visitors.ContextManager;
import visitors.Interpreter;
import visitors.utils.MockedContextManager;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class VisitProgramTest {
    @Test
    void givenProgram_whenNoStatementExists_thenErrorLogIsEmpty() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(0, errorHandler.getErrorLog().size());
    }


    @Test
    void givenMainFuncCallingOtherFunc_whenCalledFuncHasOnlyOneParam_thenOneParamIsAddedToContext() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IParameter> params = new LinkedHashMap<>() {{
            put("a", new IntParameter(new Position(5, 1), "a"));
        }};
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main",
                    new LinkedHashMap<>(),
                    new CodeBlock(new Position(10, 10), List.of(new ReturnStatement(new Position(30, 30), new FunctionCall(new Position(30, 40), new Identifier(new Position(30, 40), "func"), new IntValue(new Position(30, 50), 6))))))
            );
            put("func", new IntFunctionDef(new Position(40, 1), "func",
                    params,
                    new CodeBlock(new Position(41, 10), List.of(new ReturnStatement(new Position(41, 30), new IntValue(new Position(41, 40), 0)))))
            );
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        ArrayList<Context> expectedContexts = new ArrayList<>();
        expectedContexts.add(new Context(true)); // main function context
        expectedContexts.add(new Context()); // main function code block context
        Context funcContext = new Context(true);
        funcContext.add("a", new IntValue(new Position(5, 1), 6));
        expectedContexts.add(funcContext);
        expectedContexts.add(new Context()); // func function code block context

        assertEquals(expectedContexts, interpreter.getContextManager().getContexts());
    }

    @Test
    void givenVoidFuncWithEmptyCodeBlock_whenProgramEnded_thenLastResultIsNull() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new VoidFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of())));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertNull(interpreter.getLastResult());
    }

    @Test
    void givenVoidFuncWithAssignmentStmntInCodeBlock_whenProgramEnded_thenLastResultIsNull() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new VoidFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new IntParameter(new Position(15, 15), "m"), new IntValue(new Position(15, 20), 2))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertNull(interpreter.getLastResult());
    }

    @Test
    void givenVoidFuncWithIfStmntInCodeBlock_whenProgramEnded_thenLastResultIsNull() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new VoidFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new StringValue(new Position(20, 25), "false"), new CodeBlock(new Position(21, 21), List.of()))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertNull(interpreter.getLastResult());
    }

    @Test
    void givenVoidFuncWithElseIfStmntInCodeBlock_whenProgramEnded_thenLastResultIsNull() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new VoidFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new BoolValue(new Position(20, 35), false), new CodeBlock(new Position(41, 21), List.of()), List.of(new ElseIfStatement(new Position(30, 1), new BoolValue(new Position(30, 10), true), new CodeBlock(new Position(31, 1), List.of()))))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertNull(interpreter.getLastResult());
    }

    @Test
    void givenVoidFuncWithElseStmntInCodeBlock_whenProgramEnded_thenLastResultIsNull() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new VoidFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new BoolValue(new Position(20, 35), false), new CodeBlock(new Position(41, 21), List.of()), new ElseStatement(new Position(42, 30), new CodeBlock(new Position(42, 42), List.of())))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertNull(interpreter.getLastResult());
    }

    @Test
    void givenVoidFuncWithWhileStmntInCodeBlock_whenProgramEnded_thenLastResultIsNull() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new VoidFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new WhileStatement(new Position(20, 20), new BoolValue(new Position(20, 35), false), new CodeBlock(new Position(41, 21), List.of()))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertNull(interpreter.getLastResult());
    }

    @Test
    void givenVoidFuncWithFunctionCallInCodeBlock_whenProgramEnded_thenLastResultIsNull() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new VoidFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new FunctionCall(new Position(4, 4), new Identifier(new Position(4, 4), "getTwo"))
            ))));
            put("getTwo", new IntFunctionDef(new Position(10, 10), "getTwo", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 45), 2)))
            )));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertNull(interpreter.getLastResult());
    }

    @Test
    void givenVoidFuncWithObjectAccessInCodeBlock_whenProgramEnded_thenLastResultIsNull() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new VoidFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(60, 1), new SceneParameter(new Position(60, 1), "myList"), new SceneValue(new Position(60, 10))),
                    new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "add"), new FigureValue(new Position(65, 15))))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertNull(interpreter.getLastResult());
    }
}
