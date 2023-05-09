package parser.program_components;

import lexer.TokenTypeEnum;
import parser.IVisitable;
import parser.IVisitor;

public record Parameter(TokenTypeEnum tokenType, String name) implements IVisitable {

    @Override
    public void accept(IVisitor visitor) {

    }
}
