package parser;

import lexer.Position;

public interface IVisitable {
    Position position();

    void accept(IVisitor visitor);
}
