package parser.program_components;

import parser.IVisitable;
import parser.IVisitor;

import java.util.HashMap;

public record Program(HashMap<String, FunctionDef> functions) implements IVisitable {

    @Override
    public void accept(IVisitor visitor) {

    }
}
