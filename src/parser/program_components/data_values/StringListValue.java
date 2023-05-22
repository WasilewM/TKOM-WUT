package parser.program_components.data_values;

import lexer.Position;
import parser.IExpression;
import visitors.IVisitor;

import java.util.ArrayList;

public record StringListValue(Position position, ArrayList<StringValue> value) implements IExpression {
    public StringListValue(Position position) {
        this(position, new ArrayList<>());
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
