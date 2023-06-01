package parser;

import visitors.exceptions.IncompatibleDataTypeException;
import visitors.exceptions.IncompatibleMethodArgumentException;

public interface IExtendableDataValue extends IDataValue {
    void add(IDataValue value) throws IncompatibleMethodArgumentException, IncompatibleDataTypeException;

    Object get(int idx);

    int size();
}
