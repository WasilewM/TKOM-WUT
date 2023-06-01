package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IFunctionDef;
import parser.IParameter;
import parser.program_components.CodeBlock;
import parser.program_components.FunctionCall;
import parser.program_components.Identifier;
import parser.program_components.Program;
import parser.program_components.data_values.IntValue;
import parser.program_components.function_definitions.IntFunctionDef;
import parser.program_components.parameters.IntParameter;
import parser.program_components.statements.ReturnStatement;
import visitors.Context;
import visitors.ContextManager;
import visitors.Interpreter;
import visitors.utils.MockedContextManager;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitProgramTest {
    @Test
    void givenProgram_whenNoStatementExists_thenErrorLogIsEmpty() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))))));
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
        HashMap<String, IParameter> params = new HashMap<>() {{
            put("a", new IntParameter(new Position(5, 1), "a"));
        }};
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main",
                    new HashMap<>(),
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
}
