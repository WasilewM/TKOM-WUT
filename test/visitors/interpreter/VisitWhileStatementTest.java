package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IFunctionDef;
import parser.program_components.CodeBlock;
import parser.program_components.Identifier;
import parser.program_components.Program;
import parser.program_components.data_values.BoolValue;
import parser.program_components.data_values.IntValue;
import parser.program_components.expressions.GreaterThanExpression;
import parser.program_components.expressions.SubtractionExpression;
import parser.program_components.function_definitions.IntFunctionDef;
import parser.program_components.parameters.IntParameter;
import parser.program_components.parameters.ReassignedParameter;
import parser.program_components.statements.AssignmentStatement;
import parser.program_components.statements.IfStatement;
import parser.program_components.statements.ReturnStatement;
import parser.program_components.statements.WhileStatement;
import visitors.ContextManager;
import visitors.Interpreter;
import visitors.utils.MockedContextManager;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitWhileStatementTest {
    @Test
    void givenWhileStmntWithReturnStmntInsideCodeBlock_whenConditionIsTrue_thenExecuteCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 13);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new WhileStatement(new Position(20, 20), new IntValue(new Position(20, 25), 1), new CodeBlock(new Position(21, 21), List.of(
                            new ReturnStatement(new Position(23, 30), expectedLastResult)
                    ))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(32, 40), 40))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenWhileStmntWithReturnStmntInsideCodeBlock_whenConditionIsFalse_thenDontExecuteCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(32, 40), 40);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new WhileStatement(new Position(20, 20), new IntValue(new Position(20, 25), 0), new CodeBlock(new Position(21, 21), List.of(
                            new ReturnStatement(new Position(23, 30), new IntValue(new Position(23, 40), 13))
                    ))),
                    new ReturnStatement(new Position(30, 30), expectedLastResult)
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenWhileStmntWithParamReassignmentInsideCodeBlock_whenConditionIsTrueOnlyOnce_thenExecuteCodeBlock() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(25, 30), 0);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new IntParameter(new Position(15, 15), "a"), new IntValue(new Position(15, 20), 1)),
                    new WhileStatement(new Position(20, 20), new Identifier(new Position(20, 25), "a"), new CodeBlock(new Position(21, 21), List.of(
                            new AssignmentStatement(new Position(25, 25), new ReassignedParameter(new Position(25, 25), "a"), new IntValue(new Position(25, 30), 0))
                    ))),
                    new ReturnStatement(new Position(30, 30), new Identifier(new Position(32, 40), "a"))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenWhileStmntWithParamReassignmentInsideCodeBlock_whenConditionIsTrueTwice_thenExecuteCodeBlockTwice() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(25, 30), 0);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new IntParameter(new Position(15, 15), "a"), new IntValue(new Position(15, 20), 2)),
                    new WhileStatement(new Position(20, 20), new GreaterThanExpression(new Position(20, 25), new Identifier(new Position(20, 25), "a"), new IntValue(new Position(20, 30), 0)), new CodeBlock(new Position(21, 21), List.of(
                            new AssignmentStatement(new Position(25, 25), new ReassignedParameter(new Position(25, 25), "a"), new SubtractionExpression(new Position(25, 30), new Identifier(new Position(25, 30), "a"), new IntValue(new Position(25, 35), 1)))
                    ))),
                    new ReturnStatement(new Position(30, 30), new Identifier(new Position(32, 40), "a"))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIntFunc_whenIdentifierFromUpperContextIsBeingAccessed_thenSearchForValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(26, 15), 2);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new IntParameter(new Position(15, 20), "a"), new IntValue(new Position(15, 25), 1)),
                    new IfStatement(new Position(25, 15), new BoolValue(new Position(25, 20), true), new CodeBlock(new Position(26, 1), List.of(
                            new AssignmentStatement(new Position(26, 10), new ReassignedParameter(new Position(26, 10), "a"), expectedLastResult)
                    ))),
                    new ReturnStatement(new Position(35, 15), new Identifier(new Position(35, 25), "a")))
            )));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }
}
