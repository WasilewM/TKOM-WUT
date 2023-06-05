package visitors.interpreter;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IExpression;
import parser.IFunctionDef;
import parser.IParameter;
import parser.program_components.*;
import parser.program_components.data_values.*;
import parser.program_components.expressions.AdditionExpression;
import parser.program_components.expressions.MultiplicationExpression;
import parser.program_components.expressions.SubtractionExpression;
import parser.program_components.function_definitions.IntFunctionDef;
import parser.program_components.parameters.*;
import parser.program_components.statements.AssignmentStatement;
import parser.program_components.statements.ElseStatement;
import parser.program_components.statements.IfStatement;
import parser.program_components.statements.ReturnStatement;
import visitors.ContextManager;
import visitors.Interpreter;
import visitors.utils.MockedContextManager;
import visitors.utils.MockedExitInterpreterErrorHandler;

import java.util.ArrayList;
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
        functions.put("getTwo", new IntFunctionDef(new Position(1, 1), "getTwo", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                new ReturnStatement(new Position(30, 30), expectedLastResult))
        )));
        functions.put("main", new IntFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
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
        functions.put("getTwo", new IntFunctionDef(new Position(1, 1), "getTwo", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                new ReturnStatement(new Position(30, 30), new IntValue(new Position(30, 50), 2)))
        )));
        functions.put("main", new IntFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new AssignmentStatement(new Position(60, 60), new IntParameter(new Position(60, 60), "x"), new FunctionCall(new Position(70, 10), new Identifier(new Position(60, 10), "getTwo"))),
                new ReturnStatement(new Position(70, 1), new AdditionExpression(new Position(70, 10), new Identifier(new Position(70, 10), "x"), new IntValue(new Position(70, 15), 4))))
        )));
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenProgramWithTwoFunction_whenFunctionOperatesOnGivenParam_thenAnalyzeTheFuncFullyOnlyWhenTheParamIsPassed() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        IntValue expectedLastResult = new IntValue(new Position(30, 50), 10);
        LinkedHashMap<String, IParameter> params = new LinkedHashMap<>();
        params.put("x", new IntParameter(new Position(5, 5), "x"));
        functions.put("main", new IntFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new ReturnStatement(new Position(70, 1), new FunctionCall(new Position(70, 10), new Identifier(new Position(60, 10), "getTwice"), new IntValue(new Position(60, 20), 5)))
        ))));
        functions.put("getTwice", new IntFunctionDef(new Position(1, 1), "getTwice", params, new CodeBlock(new Position(10, 10), List.of(
                new ReturnStatement(new Position(30, 30), new MultiplicationExpression(new Position(30, 40), new Identifier(new Position(30, 40), "x"), new IntValue(new Position(30, 45), 2))))
        )));
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenProgramWithTwoFunctionAndSecondOneTakesTwoParams_whenCallingSecondFunction_thenStoreTwoParamsInContextManagerBeforeCallingSecondFunction() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        IntValue expectedLastResult = new IntValue(new Position(30, 50), 10);
        LinkedHashMap<String, IParameter> params = new LinkedHashMap<>();
        params.put("x", new IntParameter(new Position(5, 5), "x"));
        params.put("c", new StringParameter(new Position(5, 5), "c"));
        ArrayList<IExpression> arguments = new ArrayList<>();
        arguments.add(new IntValue(new Position(60, 20), 5));
        arguments.add(new StringValue(new Position(60, 25), "g5"));
        functions.put("main", new IntFunctionDef(new Position(50, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(50, 10), List.of(
                new ReturnStatement(new Position(70, 1), new FunctionCall(new Position(70, 10), new Identifier(new Position(60, 10), "getTwice"), arguments))
        ))));
        functions.put("getTwice", new IntFunctionDef(new Position(1, 1), "getTwice", params, new CodeBlock(new Position(10, 10), List.of(
                new ReturnStatement(new Position(30, 30), new MultiplicationExpression(new Position(30, 40), new Identifier(new Position(30, 40), "x"), new IntValue(new Position(30, 45), 2))))
        )));
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenTwoUserDeclaredFunction_whenTheseTwoFunctionsCallOneAnotherAndDecreaseTheNumberOfCallsLeftAndThisNumberIsSmallerThenFunctionCallStack_thenProgramEndsBeforeFunctionCallStackIsExceeded() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue numberOfFuncCalls = new IntValue(new Position(21, 20), 5);
        IntValue expectedLastResult = new IntValue(new Position(36, 50), 0);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        LinkedHashMap<String, IParameter> params = new LinkedHashMap<>();
        params.put("numberOfFuncCallsLeft", new IntParameter(new Position(1, 10), "numberOfFuncCallsLeft"));
        functions.put("main", new IntFunctionDef(new Position(10, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                new ReturnStatement(new Position(20, 1), new FunctionCall(new Position(20, 10), new Identifier(new Position(21, 10), "getTwice"), numberOfFuncCalls))
        ))));
        functions.put("getTwice", new IntFunctionDef(new Position(1, 1), "getTwice", params, new CodeBlock(new Position(10, 10), List.of(
                new AssignmentStatement(new Position(20, 1), new ReassignedParameter(new Identifier(new Position(20, 1), "numberOfFuncCallsLeft")), new SubtractionExpression(new Position(20, 20), new Identifier(new Position(20, 20), "numberOfFuncCallsLeft"), new IntValue(new Position(20, 30), 1))),
                new IfStatement(new Position(25, 1), new Identifier(new Position(25, 10), "numberOfFuncCallsLeft"), new CodeBlock(new Position(26, 10), List.of(
                        new ReturnStatement(new Position(30, 30), new FunctionCall(new Position(30, 40), new Identifier(new Position(30, 40), "getTriple"), new Identifier(new Position(30, 50), "numberOfFuncCallsLeft")))
                )),
                        new ElseStatement(new Position(35, 1), new CodeBlock(new Position(35, 10), List.of(
                                new ReturnStatement(new Position(36, 1), expectedLastResult)
                        ))))

        ))));
        functions.put("getTriple", new IntFunctionDef(new Position(1, 1), "getTriple", params, new CodeBlock(new Position(10, 10), List.of(
                new AssignmentStatement(new Position(20, 1), new ReassignedParameter(new Identifier(new Position(20, 1), "numberOfFuncCallsLeft")), new SubtractionExpression(new Position(20, 20), new Identifier(new Position(20, 20), "numberOfFuncCallsLeft"), new IntValue(new Position(20, 30), 1))),
                new IfStatement(new Position(25, 1), new Identifier(new Position(25, 10), "numberOfFuncCallsLeft"), new CodeBlock(new Position(26, 10), List.of(
                        new ReturnStatement(new Position(30, 30), new FunctionCall(new Position(30, 40), new Identifier(new Position(30, 40), "getTwice"), new Identifier(new Position(30, 50), "numberOfFuncCallsLeft")))
                )),
                        new ElseStatement(new Position(35, 1), new CodeBlock(new Position(35, 10), List.of(
                                new ReturnStatement(new Position(36, 1), expectedLastResult)
                        ))))

        ))));
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenUserDeclaredFunction_whenCallsItselfFourTimes_thenLastResultIsEqualToTheResultOfTheLastCall() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        ContextManager contextManager = new ContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue numberOfFuncCalls = new IntValue(new Position(21, 20), 5);
        IntValue expectedLastResult = new IntValue(new Position(36, 50), 0);
        LinkedHashMap<String, IFunctionDef> functions = new LinkedHashMap<>();
        LinkedHashMap<String, IParameter> params = new LinkedHashMap<>();
        params.put("numberOfFuncCallsLeft", new IntParameter(new Position(1, 10), "numberOfFuncCallsLeft"));
        functions.put("main", new IntFunctionDef(new Position(10, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                new ReturnStatement(new Position(20, 1), new FunctionCall(new Position(20, 10), new Identifier(new Position(21, 10), "getTwice"), numberOfFuncCalls))
        ))));
        functions.put("getTwice", new IntFunctionDef(new Position(1, 1), "getTwice", params, new CodeBlock(new Position(10, 10), List.of(
                new AssignmentStatement(new Position(20, 1), new ReassignedParameter(new Identifier(new Position(20, 1), "numberOfFuncCallsLeft")), new SubtractionExpression(new Position(20, 20), new Identifier(new Position(20, 20), "numberOfFuncCallsLeft"), new IntValue(new Position(20, 30), 1))),
                new IfStatement(new Position(25, 1), new Identifier(new Position(25, 10), "numberOfFuncCallsLeft"), new CodeBlock(new Position(26, 10), List.of(
                        new ReturnStatement(new Position(30, 30), new FunctionCall(new Position(30, 40), new Identifier(new Position(30, 40), "getTwice"), new Identifier(new Position(30, 50), "numberOfFuncCallsLeft")))
                )),
                        new ElseStatement(new Position(35, 1), new CodeBlock(new Position(35, 10), List.of(
                                new ReturnStatement(new Position(36, 1), new IntValue(new Position(36, 50), 0))
                        ))))

        ))));
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenPointValue_whenSettingValidRColorValue_thenAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(15, 20), 100);
        ArrayList<IExpression> params = new ArrayList<>();
        params.add(expectedLastResult);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new PointParameter(new Position(15, 15), "m"), new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new DoubleValue(new Position(15, 25), 11.0))),
                    new ObjectAccess(new Position(20, 1), new Identifier(new Position(20, 1), "m"), new FunctionCall(new Position(20, 3), new Identifier(new Position(20, 3), "setRColor"), params)),
                    new ReturnStatement(new Position(22, 1), new ObjectAccess(new Position(23, 1), new Identifier(new Position(23, 1), "m"), new FunctionCall(new Position(23, 3), new Identifier(new Position(23, 3), "getRColor"))))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenPointValue_whenSettingValidGColorValue_thenAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(15, 20), 111);
        ArrayList<IExpression> params = new ArrayList<>();
        params.add(expectedLastResult);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new PointParameter(new Position(15, 15), "m"), new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new DoubleValue(new Position(15, 25), 11.0))),
                    new ObjectAccess(new Position(20, 1), new Identifier(new Position(20, 1), "m"), new FunctionCall(new Position(20, 3), new Identifier(new Position(20, 3), "setGColor"), params)),
                    new ReturnStatement(new Position(22, 1), new ObjectAccess(new Position(23, 1), new Identifier(new Position(23, 1), "m"), new FunctionCall(new Position(23, 3), new Identifier(new Position(23, 3), "getGColor"))))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenPointValue_whenSettingValidBColorValue_thenAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(15, 20), 249);
        ArrayList<IExpression> params = new ArrayList<>();
        params.add(expectedLastResult);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new PointParameter(new Position(15, 15), "m"), new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new DoubleValue(new Position(15, 25), 11.0))),
                    new ObjectAccess(new Position(20, 1), new Identifier(new Position(20, 1), "m"), new FunctionCall(new Position(20, 3), new Identifier(new Position(20, 3), "setBColor"), params)),
                    new ReturnStatement(new Position(22, 1), new ObjectAccess(new Position(23, 1), new Identifier(new Position(23, 1), "m"), new FunctionCall(new Position(23, 3), new Identifier(new Position(23, 3), "getBColor"))))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenSectionValue_whenSettingValidRColorValue_thenAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(15, 20), 100);
        PointValue firstPoint = new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new DoubleValue(new Position(15, 25), 11.0));
        PointValue secondPoint = new PointValue(new Position(15, 20), new DoubleValue(new Position(16, 20), 10.0), new DoubleValue(new Position(16, 25), 11.0));
        ArrayList<IExpression> params = new ArrayList<>();
        params.add(expectedLastResult);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new SectionParameter(new Position(15, 15), "m"), new SectionValue(new Position(15, 15), firstPoint, secondPoint)),
                    new ObjectAccess(new Position(20, 1), new Identifier(new Position(20, 1), "m"), new FunctionCall(new Position(20, 3), new Identifier(new Position(20, 3), "setRColor"), params)),
                    new ReturnStatement(new Position(22, 1), new ObjectAccess(new Position(23, 1), new Identifier(new Position(23, 1), "m"), new FunctionCall(new Position(23, 3), new Identifier(new Position(23, 3), "getRColor"))))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenSectionValue_whenSettingValidGColorValue_thenAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(15, 20), 249);
        PointValue firstPoint = new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new DoubleValue(new Position(15, 25), 11.0));
        PointValue secondPoint = new PointValue(new Position(15, 20), new DoubleValue(new Position(16, 20), 10.0), new DoubleValue(new Position(16, 25), 11.0));
        ArrayList<IExpression> params = new ArrayList<>();
        params.add(expectedLastResult);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new SectionParameter(new Position(15, 15), "m"), new SectionValue(new Position(15, 15), firstPoint, secondPoint)),
                    new ObjectAccess(new Position(20, 1), new Identifier(new Position(20, 1), "m"), new FunctionCall(new Position(20, 3), new Identifier(new Position(20, 3), "setGColor"), params)),
                    new ReturnStatement(new Position(22, 1), new ObjectAccess(new Position(23, 1), new Identifier(new Position(23, 1), "m"), new FunctionCall(new Position(23, 3), new Identifier(new Position(23, 3), "getGColor"))))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }

    @Test
    void givenSectionValue_whenSettingValidBColorValue_thenAssignValue() {
        MockedExitInterpreterErrorHandler errorHandler = new MockedExitInterpreterErrorHandler();
        MockedContextManager contextManager = new MockedContextManager();
        Interpreter interpreter = new Interpreter(errorHandler, contextManager);
        IntValue expectedLastResult = new IntValue(new Position(15, 20), 249);
        PointValue firstPoint = new PointValue(new Position(15, 20), new DoubleValue(new Position(15, 20), 10.0), new DoubleValue(new Position(15, 25), 11.0));
        PointValue secondPoint = new PointValue(new Position(15, 20), new DoubleValue(new Position(16, 20), 10.0), new DoubleValue(new Position(16, 25), 11.0));
        ArrayList<IExpression> params = new ArrayList<>();
        params.add(expectedLastResult);
        HashMap<String, IFunctionDef> functions = new HashMap<>() {{
            put("main", new IntFunctionDef(new Position(1, 1), "main", new LinkedHashMap<>(), new CodeBlock(new Position(10, 10), List.of(
                    new AssignmentStatement(new Position(15, 15), new SectionParameter(new Position(15, 15), "m"), new SectionValue(new Position(15, 15), firstPoint, secondPoint)),
                    new ObjectAccess(new Position(20, 1), new Identifier(new Position(20, 1), "m"), new FunctionCall(new Position(20, 3), new Identifier(new Position(20, 3), "setBColor"), params)),
                    new ReturnStatement(new Position(22, 1), new ObjectAccess(new Position(23, 1), new Identifier(new Position(23, 1), "m"), new FunctionCall(new Position(23, 3), new Identifier(new Position(23, 3), "getBColor"))))
            ))));
        }};
        Program program = new Program(new Position(1, 1), functions);
        program.accept(interpreter);

        assertEquals(expectedLastResult, interpreter.getLastResult());
    }
}
