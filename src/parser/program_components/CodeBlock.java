package parser.program_components;

import lexer.Position;
import parser.IStatement;
import parser.IVisitable;
import parser.IVisitor;

import java.util.List;

public record CodeBlock(Position position, List<IStatement> statements) implements IVisitable {

    @Override
    public void accept(IVisitor visitor) {
    }
}
