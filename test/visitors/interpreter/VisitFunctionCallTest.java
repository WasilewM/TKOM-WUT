package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IFunctionDef;
import parser.program_components.CodeBlock;
import parser.program_components.FunctionCall;
import parser.program_components.Identifier;
import parser.program_components.Program;
import parser.program_components.data_values.IntValue;
import parser.program_components.expressions.AdditionExpression;
import parser.program_components.function_definitions.IntFunctionDef;
import parser.program_components.parameters.IntParameter;
import parser.program_components.statements.AssignmentStatement;
import parser.program_components.statements.ReturnStatement;
import visitors.ContextManager;
import visitors.Interpreter;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitFunctionCallTest {
    @Test
    void givenProgramWithTwoFunction_whenFunctionCallToKnownFuncIsDetected_thenFunctionCodeBlockIsExecuted() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        IntValue expectedLastResult = new IntValue(new Position(30, 50), 2);
        functions.put("getTwo", new IntFunctionDef(new Position(1, 1), "getTwo", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                new ReturnStatement(new Position(30, 30), expectedLastResult))
        )));
        functions.put("main", new IntFunctionDef(new Position(50, 1), "main", new HashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new ReturnStatement(new Position(70, 1), new FunctionCall(new Position(70, 10), new Identifier(new Position(60, 10), "getTwo")))
        ))));
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenProgramWithTwoFunction_whenFunctionCallToKnownFuncIsDetectedInsideAdditionExp_thenFunctionCodeBlockIsExecutedBeforeTheRestOfTheExp() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        IntValue expectedLastResult = new IntValue(new Position(70, 10), 6);
        functions.put("getTwo", new IntFunctionDef(new Position(1, 1), "getTwo", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 50), 2)))
        )));
        functions.put("main", new IntFunctionDef(new Position(50, 1), "main", new HashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 60), new IntParameter(new Position(60, 60), "x"), new FunctionCall(new Position(70, 10), new Identifier(new Position(60, 10), "getTwo"))),
                new ReturnStatement(new Position(70, 1), new AdditionExpression(new Position(70, 10), new Identifier(new Position(70, 10), "x"), new IntValue(new Position(70, 15), 4))))
        )));
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }
}
