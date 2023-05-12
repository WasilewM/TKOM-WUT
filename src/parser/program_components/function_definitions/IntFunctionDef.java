package parser.program_components.function_definitions;

import lexer.Position;
import parser.IFunctionDef;
import parser.IParameter;
import parser.IVisitor;
import parser.program_components.CodeBlock;
import parser.program_components.parameters.IntParameter;

import java.util.HashMap;

public record IntFunctionDef(Position position, String name, HashMap<String, IParameter> parameters,
                             CodeBlock functionCode) implements IFunctionDef {

    public IntFunctionDef(IntParameter functionType, HashMap<String, IParameter> parameters, CodeBlock codeBlock) {
        this(functionType.position(), functionType.name(), parameters, codeBlock);
    }

    @Override
    public void accept(IVisitor visitor) {
    }
}
