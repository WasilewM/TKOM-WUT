package parser.program_components.function_definitions;

import lexer.Position;
import parser.IFunctionDef;
import parser.IParameter;
import visitors.IVisitor;
import parser.program_components.CodeBlock;
import parser.program_components.parameters.DoubleParameter;

import java.util.HashMap;

public record DoubleFunctionDef(Position position, String name, HashMap<String, IParameter> parameters,
                                CodeBlock functionCode) implements IFunctionDef {

    public DoubleFunctionDef(DoubleParameter functionType, HashMap<String, IParameter> parameters, CodeBlock codeBlock) {
        this(functionType.position(), functionType.name(), parameters, codeBlock);
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
