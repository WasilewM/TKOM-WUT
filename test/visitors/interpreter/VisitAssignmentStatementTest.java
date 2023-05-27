package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IFunctionDef;
import parser.program_components.CodeBlock;
import parser.program_components.Program;
import parser.program_components.data_values.*;
import parser.program_components.expressions.AdditionExpression;
import parser.program_components.expressions.AlternativeExpression;
import parser.program_components.expressions.ConjunctiveExpression;
import parser.program_components.function_definitions.IntFunctionDef;
import parser.program_components.parameters.*;
import parser.program_components.statements.AssignmentStatement;
import parser.program_components.statements.ReturnStatement;
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
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new IntParameter(new Position(15, 15), "m"), new IntValue(new Position(15, 20), 2)),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
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
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new IntParameter(new Position(15, 15), "m"), new DoubleValue(new Position(15, 20), 2.0)),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
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
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new IntParameter(new Position(15, 15), "m"), new DoubleValue(new Position(15, 20), 2.51)),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
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
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new DoubleParameter(new Position(15, 15), "m"), new DoubleValue(new Position(15, 20), 2.21)),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
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
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new DoubleParameter(new Position(15, 15), "m"), new IntValue(new Position(15, 20), 5)),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
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
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new StringParameter(new Position(15, 15), "m"), new StringValue(new Position(15, 20), "a-2.21_b")),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
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
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new BoolParameter(new Position(15, 15), "m"), new BoolValue(new Position(15, 20), true)),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
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
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new BoolParameter(new Position(15, 15), "m"), new BoolValue(new Position(15, 20), false)),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
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
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new PointParameter(new Position(15, 15), "m"), new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new DoubleValue(new Position(15, 25), 11.0))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
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
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new PointParameter(new Position(15, 15), "m"), new PointValue(new Position(15, 20), new IntValue(new Position(15, 20), 10), new IntValue(new Position(15, 25), 11))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
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
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new PointParameter(new Position(15, 15), "m"), new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new IntValue(new Position(15, 25), 11))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
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
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new SectionParameter(new Position(15, 15), "m"), new SectionValue(new Position(15, 20), new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new IntValue(new Position(15, 25), 11)), new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 30), 23.0), new IntValue(new Position(15, 35), 21)))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
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
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new FigureParameter(new Position(15, 15), "m"), new FigureValue(new Position(15, 20))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
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
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new SceneParameter(new Position(15, 15), "m"), new SceneValue(new Position(15, 20))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new SceneValue(new Position(15, 20)));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenAssignmentStmnt_whenIntListParamAndIntListValue_thenAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new IntListParameter(new Position(15, 15), "m"), new IntListValue(new Position(15, 20))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new IntListValue(new Position(15, 20)));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenAssignmentStmnt_whenDoubleListParamAndDoubleListValue_thenAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new DoubleListParameter(new Position(15, 15), "m"), new DoubleListValue(new Position(15, 20))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new DoubleListValue(new Position(15, 20)));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenAssignmentStmnt_whenStringListParamAndStringListValue_thenAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new StringListParameter(new Position(15, 15), "m"), new StringListValue(new Position(15, 20))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new StringListValue(new Position(15, 20)));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenAssignmentStmnt_whenBoolListParamAndBoolListValue_thenAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new BoolListParameter(new Position(15, 15), "m"), new BoolListValue(new Position(15, 20))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new BoolListValue(new Position(15, 20)));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenAssignmentStmnt_whenPointListParamAndPointListValue_thenAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new PointListParameter(new Position(15, 15), "m"), new PointListValue(new Position(15, 20))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new PointListValue(new Position(15, 20)));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenAssignmentStmnt_whenSectionListParamAndSectionListValue_thenAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new SectionListParameter(new Position(15, 15), "m"), new SectionListValue(new Position(15, 20))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new SectionListValue(new Position(15, 20)));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenAssignmentStmnt_whenFigureListParamAndFigureListValue_thenAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new FigureListParameter(new Position(15, 15), "m"), new FigureListValue(new Position(15, 20))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new FigureListValue(new Position(15, 20)));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenAssignmentStmnt_whenSceneListParamAndSceneListValue_thenAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new SceneListParameter(new Position(15, 15), "m"), new SceneListValue(new Position(15, 20))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new SceneListValue(new Position(15, 20)));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenIntParam_whenReassigningIntValue_thenValueIsReassigned() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new IntParameter(new Position(15, 15), "m"), new IntValue(new Position(15, 20), 2)),
                    new AssignmentStatement(new Position(18, 15), new ReassignedParameter(new Position(18, 15), "m"), new IntValue(new Position(18, 20), 41)),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new IntValue(new Position(18, 20), 41));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenIntParam_whenReassigningDoubleValue_thenValueIsReassignedWithImplicitCast() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new IntParameter(new Position(15, 15), "m"), new IntValue(new Position(15, 20), 2)),
                    new AssignmentStatement(new Position(18, 15), new ReassignedParameter(new Position(18, 15), "m"), new DoubleValue(new Position(18, 20), 51.602)),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new IntValue(new Position(18, 20), 51));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenDoubleParam_whenReassigningIntValue_thenValueIsReassignedWithImplicitCast() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new DoubleParameter(new Position(15, 15), "m"), new DoubleValue(new Position(15, 20), 202.12)),
                    new AssignmentStatement(new Position(18, 15), new ReassignedParameter(new Position(18, 15), "m"), new IntValue(new Position(18, 20), 45)),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new DoubleValue(new Position(18, 20), 45.0));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenDoubleParam_whenArithmeticExp_thenVisitExpAndAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new DoubleParameter(new Position(15, 15), "m"), new AdditionExpression(new Position(15, 20), new DoubleValue(new Position(15, 20), 3.14), new DoubleValue(new Position(15, 25), 7.18))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new DoubleValue(new Position(15, 20), 10.32));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenIntParam_whenArithmeticExp_thenVisitExpAndAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new IntParameter(new Position(15, 15), "m"), new AdditionExpression(new Position(15, 20), new DoubleValue(new Position(15, 20), 3.14), new DoubleValue(new Position(15, 25), 7.18))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new IntValue(new Position(15, 20), 10));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenBoolParam_whenAlternativeExp_thenVisitExpAndAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new BoolParameter(new Position(15, 15), "m"), new AlternativeExpression(new Position(15, 20), new BoolValue(new Position(15, 20), false), new BoolValue(new Position(15, 25), true))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new BoolValue(new Position(15, 20), true));
        assertEquals(expected, interpreter.getLastContext());
    }

    @Test
    void givenBoolParam_whenConjunctiveExp_thenVisitExpAndAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextDeletionInterpreter interpreter = new MockedContextDeletionInterpreter(errorHandler);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new HashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new BoolParameter(new Position(15, 15), "m"), new ConjunctiveExpression(new Position(15, 20), new BoolValue(new Position(15, 20), true), new BoolValue(new Position(15, 25), false))),
                    new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 40), 0))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        interpreter.visit(program);

        Context expected = new Context();
        expected.add("m", new BoolValue(new Position(15, 20), false));
        assertEquals(expected, interpreter.getLastContext());
    }
}
