package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IFunctionDef;
import parser.IParameter;
import parser.program_components.CodeBlock;
import parser.program_components.Program;
import parser.program_components.data_values.IntValue;
import parser.program_components.function_definitions.IntFunctionDef;
import parser.program_components.parameters.DoubleParameter;
import parser.program_components.parameters.IntParameter;
import parser.program_components.parameters.StringListParameter;
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
        interpreter.visit(program);

        assertEquals(0, errorHandler.getErrorLog().size());
    }


    @Test
    void givenProgramWithOnlyMainFunc_whenFuncHasOnlyOneParam_thenOneParamIsAddedToContext() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IParameter> params = new HashMap<>() {{
            put("a", new IntParameter(new Position(5, 1), "a"));
        }};
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main",
                    params,
                    new CodeBlock(new Position(10, 10), List.of(new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0)))))
            );
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context mainFuncContext = new Context();
        mainFuncContext.add("a", new IntParameter(new Position(5, 1), "a"));
        ArrayList<Context> expectedContexts = new ArrayList<>();
        expectedContexts.add(mainFuncContext);
        expectedContexts.add(new Context());

        assertEquals(expectedContexts, interpreter.getContextManager().getContexts());
    }


    @Test
    void givenProgramWithOnlyMainFunc_whenFuncHasMultipleParams_thenAllAreAddedToContext() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IParameter> params = new HashMap<>() {{
            put("a", new IntParameter(new Position(5, 1), "a"));
            put("b", new DoubleParameter(new Position(5, 1), "b"));
            put("c", new StringListParameter(new Position(5, 1), "c"));
        }};
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main",
                    params,
                    new CodeBlock(new Position(10, 10), List.of(new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0)))))
            );
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context mainFuncContext = new Context();
        mainFuncContext.add("a", new IntParameter(new Position(5, 1), "a"));
        mainFuncContext.add("b", new DoubleParameter(new Position(5, 1), "b"));
        mainFuncContext.add("c", new StringListParameter(new Position(5, 1), "c"));
        ArrayList<Context> expectedContexts = new ArrayList<>();
        expectedContexts.add(mainFuncContext);
        expectedContexts.add(new Context());

        assertEquals(expectedContexts, interpreter.getContextManager().getContexts());
    }
}
