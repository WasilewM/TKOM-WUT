package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IFunctionDef;
import parser.program_components.*;
import parser.program_components.data_values.*;
import parser.program_components.function_definitions.BoolFunctionDef;
import parser.program_components.function_definitions.DoubleFunctionDef;
import parser.program_components.function_definitions.IntFunctionDef;
import parser.program_components.parameters.BoolListParameter;
import parser.program_components.parameters.DoubleListParameter;
import parser.program_components.parameters.IntListParameter;
import parser.program_components.statements.AssignmentStatement;
import parser.program_components.statements.ReturnStatement;
import visitors.ContextManager;
import visitors.Interpreter;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitObjectAccessTest {
    @Test
    void givenObjectAccessStmntWithIntList_whenMethodImplemented_thenExecuteMethod() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        IntValue expectedLastResult = new IntValue(new Position(65, 10), 57);
        functions.put("main", new IntFunctionDef(new Position(50, 1), "main", new HashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new IntListParameter(new Position(60, 1), "myList"), new IntListValue(new Position(60, 10))),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "add"), expectedLastResult)),
                new ReturnStatement(new Position(70, 1),
                        new ObjectAccess(new Position(75, 1), new Identifier(new Position(75, 1), "myList"), new FunctionCall(new Position(75, 8), new Identifier(new Position(75, 8), "get"), new IntValue(new Position(65, 10), 0))))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenObjectAccessStmntWithDoubleList_whenMethodImplemented_thenExecuteMethod() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        DoubleValue expectedLastResult = new DoubleValue(new Position(65, 10), 57.6);
        functions.put("main", new DoubleFunctionDef(new Position(50, 1), "main", new HashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new DoubleListParameter(new Position(60, 1), "myList"), new DoubleListValue(new Position(60, 10))),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "add"), expectedLastResult)),
                new ReturnStatement(new Position(70, 1),
                        new ObjectAccess(new Position(75, 1), new Identifier(new Position(75, 1), "myList"), new FunctionCall(new Position(75, 8), new Identifier(new Position(75, 8), "get"), new IntValue(new Position(75, 10), 0))))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenObjectAccessStmntWithBoolList_whenMethodImplemented_thenExecuteMethod() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        BoolValue expectedLastResult = new BoolValue(new Position(65, 10), true);
        functions.put("main", new BoolFunctionDef(new Position(50, 1), "main", new HashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 1), new BoolListParameter(new Position(60, 1), "myList"), new BoolListValue(new Position(60, 10))),
                new ObjectAccess(new Position(65, 1), new Identifier(new Position(65, 1), "myList"), new FunctionCall(new Position(65, 8), new Identifier(new Position(65, 8), "add"), expectedLastResult)),
                new ReturnStatement(new Position(70, 1),
                        new ObjectAccess(new Position(75, 1), new Identifier(new Position(75, 1), "myList"), new FunctionCall(new Position(75, 8), new Identifier(new Position(75, 8), "get"), new IntValue(new Position(75, 10), 0))))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }
}
