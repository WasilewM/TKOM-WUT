package parser.program_components.data_values.lists;

import lexer.Position;
import parser.IDataValue;
import parser.IExtendableDataValue;
import parser.program_components.data_values.IntValue;
import visitors.IVisitor;
import visitors.exceptions.IncompatibleDataTypeException;

import java.util.ArrayList;

public class GenericListValue implements IExtendableDataValue {
    protected final ArrayList<IDataValue> values;
    protected final Position position;

    public GenericListValue(Position position, ArrayList<IDataValue> values) {
        this.values = values;
        this.position = position;
    }

    @Override
    public ArrayList<IDataValue> value() {
        return values;
    }

    @Override
    public Position position() {
        return position;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public IDataValue get(IntValue idx) {
        return values.get(idx.value());
    }

    @Override
    public void add(IDataValue val) throws IncompatibleDataTypeException {
        values.add(val);
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
        System.out.print(getPrinting());
    }
}
