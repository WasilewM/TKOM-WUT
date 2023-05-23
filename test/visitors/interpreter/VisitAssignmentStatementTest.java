package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IFunctionDef;
import parser.program_components.CodeBlock;
import parser.program_components.Program;
import parser.program_components.data_values.*;
import parser.program_components.function_definitions.IntFunctionDef;
import parser.program_components.parameters.*;
import parser.program_components.statements.AssignmentStatement;
import visitors.Context;
import visitors.utils.MockedContextDeletionInterpreter;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitAssignmentStatementTest {
    @Test
    void givenAssignmentStmnt_whenIntParamAndIntValue_thenAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new IntParameter(new Position(15, 15), "m"), new IntValue(new Position(15, 20), 2))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new IntValue(new Position(15, 20), 2));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenAssignmentStmnt_whenIntParamAndRoundDoubleValue_thenImplicitCastToInt() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new IntParameter(new Position(15, 15), "m"), new DoubleValue(new Position(15, 20), 2.0))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new IntValue(new Position(15, 20), 2));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenAssignmentStmnt_whenIntParamAndDoubleValue_thenImplicitCastToInt() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new IntParameter(new Position(15, 15), "m"), new DoubleValue(new Position(15, 20), 2.51))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new IntValue(new Position(15, 20), 2));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenAssignmentStmnt_whenDoubleParamAndDoubleValue_thenAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new DoubleParameter(new Position(15, 15), "m"), new DoubleValue(new Position(15, 20), 2.21))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new DoubleValue(new Position(15, 20), 2.21));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenAssignmentStmnt_whenDoubleParamAndIntValue_thenImplicitCastToDouble() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new DoubleParameter(new Position(15, 15), "m"), new IntValue(new Position(15, 20), 5))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new DoubleValue(new Position(15, 20), 5.0));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenAssignmentStmnt_whenStringParamAndStringValue_thenAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new StringParameter(new Position(15, 15), "m"), new StringValue(new Position(15, 20), "a-2.21_b"))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new StringValue(new Position(15, 20), "a-2.21_b"));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenAssignmentStmnt_whenBoolParamAndBoolTrueValue_thenAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new BoolParameter(new Position(15, 15), "m"), new BoolValue(new Position(15, 20), true))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new BoolValue(new Position(15, 20), true));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenAssignmentStmnt_whenBoolParamAndBoolFalseValue_thenAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new BoolParameter(new Position(15, 15), "m"), new BoolValue(new Position(15, 20), false))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new BoolValue(new Position(15, 20), false));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenAssignmentStmnt_whenPointParamAndPointValueFromDoubleValues_thenAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new PointParameter(new Position(15, 15), "m"), new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new DoubleValue(new Position(15, 25), 11.0)))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new DoubleValue(new Position(15, 25), 11.0)));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenAssignmentStmnt_whenPointParamAndPointValueFromIntValues_thenAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new PointParameter(new Position(15, 15), "m"), new PointValue(new Position(15, 20), new IntValue(new Position(15, 20), 10), new IntValue(new Position(15, 25), 11)))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new PointValue(new Position(15, 20), new IntValue(new Position(15, 20), 10), new IntValue(new Position(15, 25), 11)));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenAssignmentStmnt_whenPointParamAndPointValueFromOneIntAndOneDouble_thenAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new PointParameter(new Position(15, 15), "m"), new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new IntValue(new Position(15, 25), 11)))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new IntValue(new Position(15, 25), 11)));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenAssignmentStmnt_whenSectionParamAndSectionValue_thenAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new SectionParameter(new Position(15, 15), "m"), new SectionValue(new Position(15, 20), new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new IntValue(new Position(15, 25), 11)), new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 30), 23.0), new IntValue(new Position(15, 35), 21))))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new SectionValue(new Position(15, 20), new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new IntValue(new Position(15, 25), 11)), new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 30), 23.0), new IntValue(new Position(15, 35), 21))));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenAssignmentStmnt_whenFigureParamAndFigureValue_thenAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new FigureParameter(new Position(15, 15), "m"), new FigureValue(new Position(15, 20)))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new FigureValue(new Position(15, 20)));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenAssignmentStmnt_whenSceneParamAndSceneValue_thenAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(new AssignmentStatement(new Position(15, 15), new SceneParameter(new Position(15, 15), "m"), new SceneValue(new Position(15, 20)))))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new SceneValue(new Position(15, 20)));
        assertEquals(expected, interpreter.getLastContext());
    }
}
