package parser;

public interface IDataValue extends IExpression {
    Object value();

    String getPrinting();

    void print();
}
