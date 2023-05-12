package parser.program_components.data_values;

import lexer.Position;
import parser.IExpression;
import parser.IVisitor;

import java.util.ArrayList;

public record SectionListValue(Position position, ArrayList<Integer> value) implements IExpression {
    public SectionListValue(Position position) {
        this(position, new ArrayList<>());
    }

    @Override
    public void accept(IVisitor visitor) {

    }
}