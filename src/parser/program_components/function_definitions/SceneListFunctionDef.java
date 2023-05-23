package parser.program_components.function_definitions;

import lexer.Position;
import parser.IFunctionDef;
import parser.IParameter;
import parser.program_components.CodeBlock;
import parser.program_components.parameters.SceneListParameter;
import visitors.IVisitor;

import java.util.HashMap;

public record SceneListFunctionDef(Position position, String name, HashMap<String, IParameter> parameters,
                                   CodeBlock functionCode) implements IFunctionDef {

    public SceneListFunctionDef(SceneListParameter functionType, HashMap<String, IParameter> parameters, CodeBlock codeBlock) {
        this(functionType.position(), functionType.name(), parameters, codeBlock);
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
