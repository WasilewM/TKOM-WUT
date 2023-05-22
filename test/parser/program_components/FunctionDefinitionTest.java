package parser.program_components;

import lexer.Position;
import org.junit.jupiter.api.Test;
import parser.IParameter;
import parser.program_components.function_definitions.IntFunctionDef;
import parser.program_components.parameters.IntParameter;
import parser.program_components.parameters.PointParameter;
import parser.program_components.parameters.SectionParameter;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FunctionDefinitionTest {
    @Test
    void evaluateCorrectParams() {
        HashMap<String, IParameter> params = new HashMap<>() {{
            put("a", new IntParameter(new Position(10, 10), "a"));
        }};
        IntFunctionDef func = new IntFunctionDef(new Position(8, 8), "a", params, new CodeBlock(new Position(11, 11), new ArrayList<>()));

        ArrayList<IParameter> paramsToBeChecked = new ArrayList<>();
        paramsToBeChecked.add(new IntParameter(new Position(10, 10), "a"));
        assertTrue(func.areParametersTypesValid(paramsToBeChecked));
    }

    @Test
    void evaluateToShortParamList() {
        HashMap<String, IParameter> params = new HashMap<>() {{
            put("a", new IntParameter(new Position(10, 10), "a"));
        }};
        IntFunctionDef func = new IntFunctionDef(new Position(8, 8), "a", params, new CodeBlock(new Position(11, 11), new ArrayList<>()));

        ArrayList<IParameter> paramsToBeChecked = new ArrayList<>();
        assertFalse(func.areParametersTypesValid(paramsToBeChecked));
    }

    @Test
    void evaluateValidParamListOfDifferentParamTypes() {
        HashMap<String, IParameter> params = new HashMap<>() {{
            put("a", new IntParameter(new Position(10, 10), "a"));
            put("b", new PointParameter(new Position(20, 20), "b"));
        }};
        IntFunctionDef func = new IntFunctionDef(new Position(8, 8), "a", params, new CodeBlock(new Position(11, 11), new ArrayList<>()));

        ArrayList<IParameter> paramsToBeChecked = new ArrayList<>();
        paramsToBeChecked.add(new IntParameter(new Position(10, 10), "a"));
        paramsToBeChecked.add(new PointParameter(new Position(20, 20), "b"));
        assertTrue(func.areParametersTypesValid(paramsToBeChecked));
    }

    @Test
    void evaluateParamListOfNotMatchingParamTypes() {
        HashMap<String, IParameter> params = new HashMap<>() {{
            put("a", new IntParameter(new Position(10, 10), "a"));
            put("b", new PointParameter(new Position(20, 20), "b"));
        }};
        IntFunctionDef func = new IntFunctionDef(new Position(8, 8), "a", params, new CodeBlock(new Position(11, 11), new ArrayList<>()));

        ArrayList<IParameter> paramsToBeChecked = new ArrayList<>();
        paramsToBeChecked.add(new IntParameter(new Position(10, 10), "a"));
        paramsToBeChecked.add(new SectionParameter(new Position(20, 20), "b"));
        assertFalse(func.areParametersTypesValid(paramsToBeChecked));
    }
}
