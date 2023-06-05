package parser.program_components.data_values;

import lexer.Position;
import parser.IDataValue;
import parser.IExtendableDataValue;
import visitors.IVisitor;
import visitors.exceptions.IncompatibleDataTypeException;
import visitors.exceptions.IncompatibleMethodArgumentException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;

public class FigureValue implements IExtendableDataValue {
    private final Position position;
    private final ArrayList<SectionValue> values;

    public FigureValue(Position position, ArrayList<SectionValue> values) {
        this.position = position;
        this.values = values;
    }

    public FigureValue(Position position) {
        this(position, new ArrayList<>());
    }

    private static boolean areSectionsConnected(SectionValue previousSection, SectionValue newSection) {
        return previousSection.first().equals(newSection.first()) || previousSection.first().equals(newSection.second())
                || previousSection.second().equals(newSection.first()) || previousSection.second().equals(newSection.second());
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public ArrayList<SectionValue> value() {
        return values;
    }

    public void add(SectionValue section) {
        values.add(section);
    }

    @Override
    public Position position() {
        return position;
    }

    public ArrayList<SectionValue> values() {
        return values;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (FigureValue) obj;
        return Objects.equals(this.position, that.position) &&
                Objects.equals(this.values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, values);
    }

    @Override
    public String toString() {
        return "FigureValue[" +
                "position=" + position + ", " +
                "values=" + values + ']';
    }

    @Override
    public void add(IDataValue value) throws IncompatibleMethodArgumentException, IncompatibleDataTypeException {
        if (value.getClass().equals(SectionValue.class)) {
            if (values.size() > 0) {
                SectionValue previousSection = values.get(values.size() - 1);
                SectionValue newSection = (SectionValue) value;
                if (!areSectionsConnected(previousSection, newSection)) {
                    throw new IncompatibleMethodArgumentException(this, newSection);
                }
            }
            values.add((SectionValue) value);
        } else {
            throw new IncompatibleDataTypeException(this, value);
        }
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public SectionValue get(IntValue idx) {
        return values.get(idx.value());
    }

    @Override
    public String getPrinting() {
        StringBuilder printing = new StringBuilder();
        printing.append("Figure[");

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
        for (SectionValue s : values) {
            s.draw(frame);
        }
    }
}
