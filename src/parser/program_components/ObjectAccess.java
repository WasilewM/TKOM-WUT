package parser.program_components;

import lexer.Position;
import parser.IExpOrStmnt;
import parser.IExpression;
import parser.IVisitor;

public record ObjectAccess(Position position, IExpression leftExp, IExpression rightExp) implements IExpOrStmnt {

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
