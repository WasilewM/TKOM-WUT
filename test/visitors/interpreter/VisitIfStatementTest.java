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
import parser.program_components.expressions.AlternativeExpression;
import parser.program_components.expressions.ConjunctiveExpression;
import parser.program_components.function_definitions.IntFunctionDef;
import parser.program_components.statements.IfStatement;
import parser.program_components.statements.ReturnStatement;
import visitors.utils.MockedContextDeletionInterpreter;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitIfStatementTest {
    @Test
    void givenIfStmnt_whenPositiveIntValueAsCondition_thenCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 13);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new IntValue(new Position(20, 25), 1), new CodeBlock(new Position(21, 21), List.of(
                            new ReturnStatement(new Position(23, 30), expectedLastResult)
                    ))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(32, 40), 40))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIfStmnt_whenZeroIntValueAsCondition_thenDontCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        IntValue expectedLastResult = new IntValue(new Position(32, 40), 40);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new IntValue(new Position(20, 25), 0), new CodeBlock(new Position(21, 21), List.of(
                            new ReturnStatement(new Position(23, 30), new IntValue(new Position(23, 40), 13))
                    ))),
                    new ReturnStatement(new Position(30, 30), expectedLastResult)
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIfStmnt_whenPositiveDoubleValueAsCondition_thenCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 13);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new DoubleValue(new Position(20, 25), 0.34), new CodeBlock(new Position(21, 21), List.of(
                            new ReturnStatement(new Position(23, 30), expectedLastResult)
                    ))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(32, 40), 40))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIfStmnt_whenZeroDoubleValueAsCondition_thenDontCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 23);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new DoubleValue(new Position(20, 25), 0.0), new CodeBlock(new Position(21, 21), List.of(
                            new ReturnStatement(new Position(23, 30), new IntValue(new Position(23, 40), 13))
                    ))),
                    new ReturnStatement(new Position(30, 30), expectedLastResult)
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIfStmnt_whenBoolTrueValueAsCondition_thenCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 13);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new BoolValue(new Position(20, 25), true), new CodeBlock(new Position(21, 21), List.of(
                            new ReturnStatement(new Position(23, 30), expectedLastResult)
                    ))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(32, 40), 40))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIfStmnt_whenBoolFalseValueAsCondition_thenDontCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 23);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new BoolValue(new Position(20, 25), false), new CodeBlock(new Position(21, 21), List.of(
                            new ReturnStatement(new Position(23, 30), new IntValue(new Position(23, 40), 13))
                    ))),
                    new ReturnStatement(new Position(30, 30), expectedLastResult)
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIfStmnt_whenValueOtherThanIntOrDoubleOrBoolAsCondition_thenDontCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 23);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new StringValue(new Position(20, 25), "false"), new CodeBlock(new Position(21, 21), List.of(
                            new ReturnStatement(new Position(23, 30), new IntValue(new Position(23, 40), 13))
                    ))),
                    new ReturnStatement(new Position(30, 30), expectedLastResult)
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIfStmntWithConjunctiveExpInCondition_whenBothSidesOAreTrue_thenCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 23);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new ConjunctiveExpression(new Position(20, 25), new BoolValue(new Position(20, 25), true), new BoolValue(new Position(20, 35), true)), new CodeBlock(new Position(21, 21), List.of(
                            new ReturnStatement(new Position(23, 30), expectedLastResult)
                    ))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(32, 40), 40))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIfStmntWithConjunctiveExpInCondition_whenLeftSideIsFalseAndRightSideIsTrue_thenDontCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 23);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new ConjunctiveExpression(new Position(20, 25), new BoolValue(new Position(20, 25), false), new BoolValue(new Position(20, 35), true)), new CodeBlock(new Position(21, 21), List.of(
                            new ReturnStatement(new Position(23, 30), new IntValue(new Position(23, 40), 13))
                    ))),
                    new ReturnStatement(new Position(30, 30), expectedLastResult)
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIfStmntWithConjunctiveExpInCondition_whenBothSidesAreFalse_thenDontCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 23);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new ConjunctiveExpression(new Position(20, 25), new BoolValue(new Position(20, 25), false), new BoolValue(new Position(20, 35), false)), new CodeBlock(new Position(21, 21), List.of(
                            new ReturnStatement(new Position(23, 30), new IntValue(new Position(23, 40), 13))
                    ))),
                    new ReturnStatement(new Position(30, 30), expectedLastResult)
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIfStmntWithConjunctiveExpInCondition_whenRightSideOfIsFalse_thenDontCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 23);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new ConjunctiveExpression(new Position(20, 25), new BoolValue(new Position(20, 25), true), new BoolValue(new Position(20, 35), false)), new CodeBlock(new Position(21, 21), List.of(
                            new ReturnStatement(new Position(23, 30), new IntValue(new Position(23, 40), 13))
                    ))),
                    new ReturnStatement(new Position(30, 30), expectedLastResult)
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIfStmntWithAlternativeExpInCondition_whenLeftSideIsFalseAndRightSideIsTrue_thenCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 23);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new AlternativeExpression(new Position(20, 25), new BoolValue(new Position(20, 25), false), new BoolValue(new Position(20, 35), true)), new CodeBlock(new Position(21, 21), List.of(
                            new ReturnStatement(new Position(23, 30), expectedLastResult)
                    ))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(32, 40), 40))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIfStmntWithAlternativeExpInCondition_whenBothSidesAreTrue_thenCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 23);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new AlternativeExpression(new Position(20, 25), new BoolValue(new Position(20, 25), true), new BoolValue(new Position(20, 35), true)), new CodeBlock(new Position(21, 21), List.of(
                            new ReturnStatement(new Position(23, 30), expectedLastResult)
                    ))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(32, 40), 40))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIfStmntWithAlternativeExpInCondition_whenLeftSideIsTrueAndRightSideIsFalse_thenCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 23);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new AlternativeExpression(new Position(20, 25), new BoolValue(new Position(20, 25), true), new BoolValue(new Position(20, 35), false)), new CodeBlock(new Position(21, 21), List.of(
                            new ReturnStatement(new Position(23, 30), expectedLastResult)
                    ))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(32, 40), 40))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIfStmntWithAlternativeExpInCondition_whenBothSideAreFalse_thenDontCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        IntValue expectedLastResult = new IntValue(new Position(23, 40), 23);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new AlternativeExpression(new Position(20, 25), new BoolValue(new Position(20, 25), false), new BoolValue(new Position(20, 35), false)), new CodeBlock(new Position(21, 21), List.of(
                            new ReturnStatement(new Position(23, 30), new IntValue(new Position(23, 40), 13))
                    ))),
                    new ReturnStatement(new Position(30, 30), expectedLastResult)
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIfStmntWithAlternativeExpInCondition_whenLeftSideIsNegativeAlternativeExpAndRightSideIsNegativeConjunctiveExp_thenDontCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        IntValue expectedLastResult = new IntValue(new Position(73, 40), 23);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new AlternativeExpression(new Position(20, 25), new AlternativeExpression(new Position(20, 25), new BoolValue(new Position(20, 25), false), new BoolValue(new Position(20, 35), false)), new ConjunctiveExpression(new Position(30, 25), new BoolValue(new Position(30, 25), false), new BoolValue(new Position(30, 35), false))), new CodeBlock(new Position(41, 21), List.of(
                            new ReturnStatement(new Position(53, 30), new IntValue(new Position(53, 40), 13))
                    ))),
                    new ReturnStatement(new Position(70, 30), expectedLastResult)
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenIfStmntWithAlternativeExpInCondition_whenLeftSideIsNegativeAlternativeExpAndRightSideIsPositiveConjunctiveExp_thenCheckIfCodeBlock() {
        // to check whether Interpreter checked if code block or not, different return values are set
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        IntValue expectedLastResult = new IntValue(new Position(53, 40), 13);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new IfStatement(new Position(20, 20), new AlternativeExpression(new Position(20, 25), new AlternativeExpression(new Position(20, 25), new BoolValue(new Position(20, 25), false), new BoolValue(new Position(20, 35), false)), new ConjunctiveExpression(new Position(30, 25), new BoolValue(new Position(30, 25), true), new BoolValue(new Position(30, 35), true))), new CodeBlock(new Position(41, 21), List.of(
                            new ReturnStatement(new Position(53, 30), expectedLastResult)
                    ))),
                    new ReturnStatement(new Position(70, 30), new IntValue(new Position(73, 40), 23))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }
}
