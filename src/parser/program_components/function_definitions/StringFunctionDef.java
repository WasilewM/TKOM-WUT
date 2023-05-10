package parser.program_components.function_definitions;

import parser.IFunctionDef;
import parser.IParameter;
import parser.IVisitor;
import parser.program_components.CodeBlock;

import java.util.HashMap;

public record StringFunctionDef(String name, HashMap<String, IParameter> parameters,
                                CodeBlock functionCode) implements IFunctionDef {

    @Override
    public void accept(IVisitor visitor) {
    }
}
