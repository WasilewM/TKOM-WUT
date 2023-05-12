package parser.program_components.data_values;

import lexer.Position;
import parser.IExpression;
import parser.IVisitor;

import java.util.ArrayList;

public record IntListValue(Position position, ArrayList<Integer> value) implements IExpression {
    public IntListValue(Position position) {
        this(position, new ArrayList<>());
    }

    @Override
    public void accept(IVisitor visitor) {

    }
}