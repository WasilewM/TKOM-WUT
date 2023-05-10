package parser.program_components.function_definitions;

import lexer.TokenTypeEnum;
import parser.IFunctionDef;
import parser.IVisitor;
import parser.program_components.CodeBlock;
import parser.program_components.Parameter;

import java.util.HashMap;

public record FunctionDef(String name, TokenTypeEnum functionType, HashMap<String, Parameter> parameters,
                          CodeBlock functionCode) implements IFunctionDef {

    @Override
    public void accept(IVisitor visitor) {
    }
}
