package parser.program_components;

import lexer.Position;
import parser.IExpOrStmnt;
import parser.IExpression;
import visitors.IVisitor;

public record FunctionCall(Position position, Identifier identifier, IExpression exp) implements IExpOrStmnt {

    public FunctionCall(Position position, Identifier identifier) {
        this(position, identifier, null);
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
