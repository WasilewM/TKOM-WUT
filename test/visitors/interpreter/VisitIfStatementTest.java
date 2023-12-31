package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IFunctionDef;
import parser.program_components.CodeBlock;
import parser.program_components.Identifier;
import parser.program_components.Program;
import parser.program_components.data_values.BoolValue;
import parser.program_components.data_values.DoubleValue;
import parser.program_components.data_values.IntValue;
import parser.program_components.data_values.StringValue;
import parser.program_components.expressions.AlternativeExpression;
import parser.program_components.expressions.ConjunctiveExpression;
import parser.program_components.function_definitions.IntFunctionDef;
import parser.program_components.parameters.IntParameter;
import parser.program_components.statements.*;
import visitors.Interpreter;
import visitors.utils.MockedContextManager;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitIfStatementTest {
    @Test
    void givenIfStmnt_whenPositiveIntValueAsCondition_thenCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 13);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new IntValue(new Position(20, 25), 1), new CodeBlock(new Position(21, 21), List.of(
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
    void givenIfStmnt_whenZeroIntValueAsCondition_thenDontCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(32, 40), 40);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new IntValue(new Position(20, 25), 0), new CodeBlock(new Position(21, 21), List.of(
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
    void givenIfStmnt_whenPositiveDoubleValueAsCondition_thenCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 13);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new DoubleValue(new Position(20, 25), 0.34), new CodeBlock(new Position(21, 21), List.of(
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
    void givenIfStmnt_whenZeroDoubleValueAsCondition_thenDontCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 23);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new DoubleValue(new Position(20, 25), 0.0), new CodeBlock(new Position(21, 21), List.of(
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
    void givenIfStmnt_whenBoolTrueValueAsCondition_thenCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 13);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new BoolValue(new Position(20, 25), true), new CodeBlock(new Position(21, 21), List.of(
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
    void givenIfStmnt_whenConditionIsTrueAndVariableFromIfCodeBlockShadowsValueAssignedEarlier_thenVariableFromInsideIfCodeBlockIsReturned() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(16, 40), 13);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new IntParameter(new Position(15, 15), "m"), new IntValue(new Position(15, 20), 2)),
                    new IfStatement(new Position(20, 20), new BoolValue(new Position(20, 25), true), new CodeBlock(new Position(21, 21), List.of(
                            new AssignmentStatement(new Position(15, 15), new IntParameter(new Position(15, 15), "m"), expectedLastResult),
                            new ReturnStatement(new Position(23, 30), new Identifier(new Position(23, 40), "m"))
                    ))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(32, 40), 40))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIfStmnt_whenBoolFalseValueAsCondition_thenDontCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 23);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new BoolValue(new Position(20, 25), false), new CodeBlock(new Position(21, 21), List.of(
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
    void givenIfStmnt_whenValueOtherThanIntOrDoubleOrBoolAsCondition_thenDontCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 23);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new StringValue(new Position(20, 25), "false"), new CodeBlock(new Position(21, 21), List.of(
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
    void givenIfStmntWithConjunctiveExpInCondition_whenBothSidesOAreTrue_thenCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 23);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new ConjunctiveExpression(new Position(20, 25), new BoolValue(new Position(20, 25), true), new BoolValue(new Position(20, 35), true)), new CodeBlock(new Position(21, 21), List.of(
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
    void givenIfStmntWithConjunctiveExpInCondition_whenLeftSideIsFalseAndRightSideIsTrue_thenDontCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 23);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new ConjunctiveExpression(new Position(20, 25), new BoolValue(new Position(20, 25), false), new BoolValue(new Position(20, 35), true)), new CodeBlock(new Position(21, 21), List.of(
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
    void givenIfStmntWithConjunctiveExpInCondition_whenBothSidesAreFalse_thenDontCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 23);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new ConjunctiveExpression(new Position(20, 25), new BoolValue(new Position(20, 25), false), new BoolValue(new Position(20, 35), false)), new CodeBlock(new Position(21, 21), List.of(
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
    void givenIfStmntWithConjunctiveExpInCondition_whenRightSideOfIsFalse_thenDontCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 23);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new ConjunctiveExpression(new Position(20, 25), new BoolValue(new Position(20, 25), true), new BoolValue(new Position(20, 35), false)), new CodeBlock(new Position(21, 21), List.of(
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
    void givenIfStmntWithAlternativeExpInCondition_whenLeftSideIsFalseAndRightSideIsTrue_thenCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 23);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new AlternativeExpression(new Position(20, 25), new BoolValue(new Position(20, 25), false), new BoolValue(new Position(20, 35), true)), new CodeBlock(new Position(21, 21), List.of(
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
    void givenIfStmntWithAlternativeExpInCondition_whenBothSidesAreTrue_thenCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 23);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new AlternativeExpression(new Position(20, 25), new BoolValue(new Position(20, 25), true), new BoolValue(new Position(20, 35), true)), new CodeBlock(new Position(21, 21), List.of(
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
    void givenIfStmntWithAlternativeExpInCondition_whenLeftSideIsTrueAndRightSideIsFalse_thenCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 23);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new AlternativeExpression(new Position(20, 25), new BoolValue(new Position(20, 25), true), new BoolValue(new Position(20, 35), false)), new CodeBlock(new Position(21, 21), List.of(
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
    void givenIfStmntWithAlternativeExpInCondition_whenBothSideAreFalse_thenDontCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 23);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new AlternativeExpression(new Position(20, 25), new BoolValue(new Position(20, 25), false), new BoolValue(new Position(20, 35), false)), new CodeBlock(new Position(21, 21), List.of(
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
    void givenIfStmntWithAlternativeExpInCondition_whenLeftSideIsNegativeAlternativeExpAndRightSideIsNegativeConjunctiveExp_thenDontCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(73, 40), 23);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new AlternativeExpression(new Position(20, 25), new AlternativeExpression(new Position(20, 25), new BoolValue(new Position(20, 25), false), new BoolValue(new Position(20, 35), false)), new ConjunctiveExpression(new Position(30, 25), new BoolValue(new Position(30, 25), false), new BoolValue(new Position(30, 35), false))), new CodeBlock(new Position(41, 21), List.of(
                            new ReturnStatement(new Position(53, 30), new IntValue(new Position(53, 40), 13))
                    ))),
                    new ReturnStatement(new Position(70, 30), expectedLastResult)
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIfStmntWithAlternativeExpInCondition_whenLeftSideIsNegativeAlternativeExpAndRightSideIsPositiveConjunctiveExp_thenCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(53, 40), 13);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new AlternativeExpression(new Position(20, 25), new AlternativeExpression(new Position(20, 25), new BoolValue(new Position(20, 25), false), new BoolValue(new Position(20, 35), false)), new ConjunctiveExpression(new Position(30, 25), new BoolValue(new Position(30, 25), true), new BoolValue(new Position(30, 35), true))), new CodeBlock(new Position(41, 21), List.of(
                            new ReturnStatement(new Position(53, 30), expectedLastResult)
                    ))),
                    new ReturnStatement(new Position(70, 30), new IntValue(new Position(73, 40), 23))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIfStmntWithSingleElseIfStatement_whenIfConditionIsTrue_thenCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(53, 40), 13);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new BoolValue(new Position(20, 35), true), new CodeBlock(new Position(41, 21), List.of(
                            new ReturnStatement(new Position(53, 30), expectedLastResult)
                    )), List.of(new ElseIfStatement(new Position(30, 1), new BoolValue(new Position(30, 10), true), new CodeBlock(new Position(31, 1), List.of(
                            new ReturnStatement(new Position(31, 10), new IntValue(new Position(33, 20), 213))
                    ))))),
                    new ReturnStatement(new Position(70, 30), new IntValue(new Position(73, 40), 23))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIfStmntWithSingleElseIfStmnt_whenIfConditionIsFalseButElseIfConditionIsTrue_thenCheckElseIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(33, 20), 213);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new BoolValue(new Position(20, 35), false), new CodeBlock(new Position(41, 21), List.of(
                            new ReturnStatement(new Position(53, 30), new IntValue(new Position(53, 40), 13))
                    )), List.of(new ElseIfStatement(new Position(30, 1), new BoolValue(new Position(30, 10), true), new CodeBlock(new Position(31, 1), List.of(
                            new ReturnStatement(new Position(31, 10), expectedLastResult)
                    ))))),
                    new ReturnStatement(new Position(70, 30), new IntValue(new Position(73, 40), 23))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIfStmntWithWithElseIfStmnts_whenBothElseIfConditionsAreTrue_thenCheckFirstElseIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(33, 20), 213);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new BoolValue(new Position(20, 35), false), new CodeBlock(new Position(41, 21), List.of(
                            new ReturnStatement(new Position(53, 30), new IntValue(new Position(53, 40), 13))
                    )), List.of(
                            new ElseIfStatement(new Position(30, 1), new BoolValue(new Position(30, 10), true), new CodeBlock(new Position(31, 1), List.of(
                                    new ReturnStatement(new Position(31, 10), expectedLastResult)))),
                            new ElseIfStatement(new Position(35, 1), new BoolValue(new Position(35, 10), true), new CodeBlock(new Position(35, 1), List.of(
                                    new ReturnStatement(new Position(36, 10), new IntValue(new Position(36, 20), 81)))))
                    )),
                    new ReturnStatement(new Position(70, 30), new IntValue(new Position(73, 40), 23))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIfStmntWithWithElseIfStmnts_whenOnlySecondElseIfConditionIsTrue_thenCheckSecondElseIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(36, 20), 81);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new BoolValue(new Position(20, 35), false), new CodeBlock(new Position(41, 21), List.of(
                            new ReturnStatement(new Position(53, 30), new IntValue(new Position(53, 40), 13))
                    )), List.of(
                            new ElseIfStatement(new Position(30, 1), new BoolValue(new Position(30, 10), false), new CodeBlock(new Position(31, 1), List.of(
                                    new ReturnStatement(new Position(31, 10), new IntValue(new Position(33, 20), 213))))),
                            new ElseIfStatement(new Position(35, 1), new BoolValue(new Position(35, 10), true), new CodeBlock(new Position(35, 1), List.of(
                                    new ReturnStatement(new Position(36, 10), expectedLastResult))))
                    )),
                    new ReturnStatement(new Position(70, 30), new IntValue(new Position(73, 40), 23))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIfStmntWithIfStmntInsideItsCodeBlock_whenExitingEachIfStmntDecreaseIfsDepth_thenAtTheEndDepthShouldEqualZero() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new BoolValue(new Position(20, 35), true), new CodeBlock(new Position(41, 21), List.of(
                            new IfStatement(new Position(30, 20), new BoolValue(new Position(30, 35), true), new CodeBlock(new Position(31, 21), List.of()))
                    ))),
                    new ReturnStatement(new Position(70, 30), new IntValue(new Position(73, 40), 23))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(0, interpreter.getIfStatementsDepth());
    }

    @Test
    void givenIfStmntWithWithElseIfStmnts_whenExitingEachIfStmntDecreaseIfsDepth_thenAtTheEndDepthShouldEqualZero() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new BoolValue(new Position(20, 35), false), new CodeBlock(new Position(41, 21), List.of(
                            new ReturnStatement(new Position(23, 30), new IntValue(new Position(23, 40), 13))
                    )), List.of(
                            new ElseIfStatement(new Position(30, 1), new BoolValue(new Position(30, 10), true), new CodeBlock(new Position(31, 1), List.of(
                                    new IfStatement(new Position(40, 20), new BoolValue(new Position(40, 35), true), new CodeBlock(new Position(41, 21), List.of(
                                            new IfStatement(new Position(50, 20), new BoolValue(new Position(50, 35), true), new CodeBlock(new Position(51, 21), List.of()))
                                    ))))
                            )
                            ))),
                    new ReturnStatement(new Position(70, 30), new IntValue(new Position(73, 40), 23))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(0, interpreter.getIfStatementsDepth());
    }

    @Test
    void givenIfStmntWithElseStatement_whenIfConditionIsTrue_thenCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 13);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new BoolValue(new Position(20, 35), true), new CodeBlock(new Position(21, 21), List.of(
                            new ReturnStatement(new Position(23, 30), expectedLastResult)
                    )),
                            new ElseStatement(new Position(30, 1), new CodeBlock(new Position(31, 1), List.of(
                                    new ReturnStatement(new Position(33, 30), new IntValue(new Position(33, 40), 13))
                            )))
                    ),
                    new ReturnStatement(new Position(70, 30), new IntValue(new Position(73, 40), 23))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIfStmntWithElseStatement_whenIfConditionIsFalse_thenCheckElseCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(33, 40), 13);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new BoolValue(new Position(20, 35), false), new CodeBlock(new Position(21, 21), List.of(
                            new ReturnStatement(new Position(23, 30), new IntValue(new Position(23, 40), 13))
                    )),
                            new ElseStatement(new Position(30, 1), new CodeBlock(new Position(31, 1), List.of(
                                    new ReturnStatement(new Position(33, 30), expectedLastResult)
                            )))
                    ),
                    new ReturnStatement(new Position(70, 30), new IntValue(new Position(73, 40), 23))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIfStmntWithSingleElseIfStmntAndElseStmnt_whenIfConditionElseIfConditionAreFalse_thenCheckElseCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(33, 40), 13);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new BoolValue(new Position(20, 35), false), new CodeBlock(new Position(41, 21), List.of(
                            new ReturnStatement(new Position(53, 30), new IntValue(new Position(53, 40), 13))
                    )), List.of(new ElseIfStatement(new Position(30, 1), new BoolValue(new Position(30, 10), false), new CodeBlock(new Position(31, 1), List.of(
                            new ReturnStatement(new Position(31, 10), new IntValue(new Position(33, 20), 213))
                    )))),
                            new ElseStatement(new Position(30, 1), new CodeBlock(new Position(31, 1), List.of(
                                    new ReturnStatement(new Position(33, 30), expectedLastResult)
                            )))),
                    new ReturnStatement(new Position(70, 30), new IntValue(new Position(73, 40), 23))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }
}
