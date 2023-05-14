package parser.program_components;

import lexer.Position;
import parser.IFunctionDef;
import parser.IVisitable;
import parser.IVisitor;

import java.util.HashMap;

public record Program(Position position, HashMap<String, IFunctionDef> functions) implements IVisitable {

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
