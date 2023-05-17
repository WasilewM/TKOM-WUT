package parser;

import lexer.Position;
import visitor.IVisitor;

public interface IVisitable {
    Position position();

    void accept(IVisitor visitor);
}
