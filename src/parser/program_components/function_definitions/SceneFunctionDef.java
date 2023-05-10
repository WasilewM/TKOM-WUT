package parser.program_components.function_definitions;

import lexer.Position;
import parser.IFunctionDef;
import parser.IParameter;
import parser.IVisitor;
import parser.program_components.CodeBlock;

import java.util.HashMap;

public record SceneFunctionDef(Position position, String name, HashMap<String, IParameter> parameters,
                               CodeBlock functionCode) implements IFunctionDef {

    @Override
    public void accept(IVisitor visitor) {
    }
}
