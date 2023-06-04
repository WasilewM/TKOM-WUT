package parser.program_components.data_values;

import lexer.Position;
import parser.IDataValue;
import parser.IExpression;
import visitors.IVisitor;

import javax.swing.*;
import java.awt.*;

public record PointValue(Position position, IExpression x, IExpression y) implements IDataValue {
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Object value() {
        return null;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof PointValue castedOther)) {
            return false;
        }

        return ((this.x).equals(castedOther.x) && (this.y).equals(castedOther.y));
    }

    @Override
    public String getPrinting() {
        return "Point(" + ((DoubleValue) x).getPrinting() + ", " + ((DoubleValue) y).getPrinting() + ")";
    }

    @Override
    public void print() {
        System.out.println(getPrinting());
    }

    public void draw(JFrame frame) {
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g0) {
                int pointSize = 5;
                DoubleValue xVal = (DoubleValue) x;
                DoubleValue yVal = (DoubleValue) y;
                Graphics2D g = (Graphics2D) g0.create();
                g.setColor(Color.BLACK);
                g.fillOval((int) (xVal.value() - pointSize / 2), (int) (yVal.value() - pointSize / 2), pointSize, pointSize);
            }
        };
        frame.add(panel);
        frame.setVisible(true);
    }
}
