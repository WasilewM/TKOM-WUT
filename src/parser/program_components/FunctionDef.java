package parser.program_components;

import lexer.TokenTypeEnum;
import parser.IVisitable;
import parser.IVisitor;

import java.util.List;

public record FunctionDef(String name, TokenTypeEnum functionType, List<Parameter> parameters,
                          BlockStatement statement) implements IVisitable {

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
