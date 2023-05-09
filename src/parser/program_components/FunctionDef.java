package parser.program_components;

import lexer.TokenTypeEnum;
import parser.IVisitable;
import parser.IVisitor;

import java.util.HashMap;

public record FunctionDef(String name, TokenTypeEnum functionType, HashMap<String, Parameter> parameters,
                          CodeBlock functionCode) implements IVisitable {

    @Override
    public void accept(IVisitor visitor) {
    }
}
