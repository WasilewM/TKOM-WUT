package parser.program_components.function_definitions;

import lexer.Position;
import parser.IFunctionDef;
import parser.IParameter;
import parser.program_components.CodeBlock;
import parser.program_components.parameters.SceneParameter;
import visitors.IVisitor;

import java.util.LinkedHashMap;

public record SceneFunctionDef(Position position, String name, LinkedHashMap<String, IParameter> parameters,
                               CodeBlock functionCode) implements IFunctionDef {

    public SceneFunctionDef(SceneParameter functionType, LinkedHashMap<String, IParameter> parameters, CodeBlock codeBlock) {
        this(functionType.position(), functionType.name(), parameters, codeBlock);
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
