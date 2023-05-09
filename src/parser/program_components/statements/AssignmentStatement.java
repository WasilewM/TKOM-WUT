package parser.program_components.statements;

import lexer.TokenTypeEnum;
import parser.IExpression;
import parser.IStatement;
import parser.IVisitor;

public record AssignmentStatement(TokenTypeEnum declaredDataType, String name,
                                  IExpression alternativeExp) implements IStatement {
    @Override
    public void accept(IVisitor visitor) {

    }
}
