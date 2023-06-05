package parser;

import lexer.Position;
import visitors.IVisitor;

public interface IVisitable {
    Position position();

    void accept(IVisitor visitor);
}
