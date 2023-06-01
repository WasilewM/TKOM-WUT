package parser.program_components.data_values;

import lexer.Position;
import parser.IDataValue;
import parser.IExpression;
import parser.IExtendableDataValue;
import visitors.IVisitor;
import visitors.exceptions.IncompatibleDataTypeException;

import java.util.ArrayList;
import java.util.Objects;

public class SceneValue implements IExtendableDataValue {
    private final Position position;
    private final ArrayList<IExpression> values;

    public SceneValue(Position position, ArrayList<IExpression> values) {
        this.position = position;
        this.values = values;
    }

    public SceneValue(Position position) {
        this(position, new ArrayList<>());
    }

    private static boolean isValueOfAcceptableDataType(IDataValue value) {
        return value.getClass().equals(FigureValue.class)
                || value.getClass().equals(PointValue.class)
                || value.getClass().equals(SectionValue.class);
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Object value() {
        return values;
    }

    @Override
    public Position position() {
        return position;
    }

    public ArrayList<IExpression> values() {
        return values;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SceneValue) obj;
        return Objects.equals(this.position, that.position) &&
                Objects.equals(this.values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, values);
    }

    @Override
    public String toString() {
        return "SceneValue[" +
                "position=" + position + ", " +
                "values=" + values + ']';
    }

    @Override
    public void add(IDataValue value) throws IncompatibleDataTypeException {
        if (isValueOfAcceptableDataType(value)) {
            values.add(value);
        } else {
            throw new IncompatibleDataTypeException(this, value);
        }
    }

    @Override
    public Object get(int idx) {
        return values.get(idx);
    }

    @Override
    public int size() {
        return values.size();
    }
}
