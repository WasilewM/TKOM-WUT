package parser.program_components.function_definitions;

import lexer.Position;
import parser.IFunctionDef;
import parser.IParameter;
import visitor.IVisitor;
import parser.program_components.CodeBlock;
import parser.program_components.parameters.BoolParameter;

import java.util.HashMap;

public record BoolFunctionDef(Position position, String name, HashMap<String, IParameter> parameters,
                              CodeBlock functionCode) implements IFunctionDef {

    public BoolFunctionDef(BoolParameter functionType, HashMap<String, IParameter> parameters, CodeBlock codeBlock) {
        this(functionType.position(), functionType.name(), parameters, codeBlock);
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
