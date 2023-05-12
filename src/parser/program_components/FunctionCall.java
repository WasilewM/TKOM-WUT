package parser.program_components;

import lexer.Position;
import parser.IExpOrStmnt;
import parser.IExpression;
import parser.IVisitor;

public record FunctionCall(Position position, IExpression identifier) implements IExpOrStmnt {

    @Override
    public void accept(IVisitor visitor) {

    }
}
