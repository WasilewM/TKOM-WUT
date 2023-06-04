package parser.program_components.data_values;

import lexer.Position;
import parser.IDataValue;
import parser.IExtendableDataValue;
import visitors.IVisitor;
import visitors.exceptions.IncompatibleDataTypeException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;

public class SceneValue implements IExtendableDataValue {
    private final Position position;
    private final ArrayList<IDataValue> values;

    public SceneValue(Position position, ArrayList<IDataValue> values) {
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
    public ArrayList<IDataValue> value() {
        return values;
    }

    @Override
    public Position position() {
        return position;
    }

    public ArrayList<IDataValue> values() {
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
    public IDataValue get(IntValue idx) {
        return values.get(idx.value());
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public String getPrinting() {
        StringBuilder printing = new StringBuilder();
        printing.append("Scene[");

        int idx = 0;
        for (IDataValue v : values) {
            if (idx > 0) {
                printing.append(", ");
            }
            printing.append(v.getPrinting());
            idx += 1;
        }
        printing.append("]");
        return printing.toString();
    }

    @Override
    public void print() {
        System.out.println(getPrinting());
    }

    public void draw(JFrame frame) {
        for (IDataValue v : values) {
            if (v instanceof PointValue) {
                ((PointValue) v).draw(frame);
            } else if (v instanceof SectionValue) {
                ((SectionValue) v).draw(frame);
            } else if (v instanceof FigureValue) {
                ((FigureValue) v).draw(frame);
            } else {
                throw new RuntimeException();
            }
        }
    }
}
