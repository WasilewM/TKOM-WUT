package parser.program_components.data_values;

import lexer.Position;
import parser.IExpression;
import visitor.IVisitor;

import java.util.ArrayList;

public record FigureListValue(Position position, ArrayList<Integer> value) implements IExpression {
    public FigureListValue(Position position) {
        this(position, new ArrayList<>());
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
