package parser.program_components.data_values;

import lexer.Position;
import parser.IDataValue;
import parser.IExpression;
import visitors.IVisitor;
import visitors.exceptions.IncompatibleMethodArgumentException;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class PointValue implements IDataValue {
    private final Position position;
    private final IExpression x;
    private final IExpression y;
    private int rColor;
    private int gColor;
    private int bColor;


    public PointValue(Position position, IExpression x, IExpression y) {
        this.position = position;
        this.x = x;
        this.y = y;
        rColor = 0;
        gColor = 0;
        bColor = 0;
    }

    private static boolean isNewColorValueValid(IDataValue newColorValue) {
        return newColorValue.getClass().equals(IntValue.class)
                && (((IntValue) newColorValue).value() >= 0 && ((IntValue) newColorValue).value() <= 255);
    }

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
                g.setColor(new Color(rColor, gColor, bColor));
                g.fillOval((int) (xVal.value() - pointSize / 2), (int) (yVal.value() - pointSize / 2), pointSize, pointSize);
            }
        };
        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public Position position() {
        return position;
    }

    public IExpression x() {
        return x;
    }

    public IExpression y() {
        return y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, x, y);
    }

    @Override
    public String toString() {
        return "PointValue[" +
                "position=" + position + ", " +
                "x=" + x + ", " +
                "y=" + y + ']';
    }

    public IntValue getRColor() {
        return new IntValue(position, rColor);
    }

    public void setRColor(IDataValue newColor) throws IncompatibleMethodArgumentException {
        if (isNewColorValueValid(newColor)) {
            rColor = ((IntValue) newColor).value();
        } else {
            throw new IncompatibleMethodArgumentException(this, newColor);
        }
    }

    public IntValue getGColor() {
        return new IntValue(position, gColor);
    }

    public void setGColor(IDataValue newColor) throws IncompatibleMethodArgumentException {
        if (isNewColorValueValid(newColor)) {
            gColor = ((IntValue) newColor).value();
        } else {
            throw new IncompatibleMethodArgumentException(this, newColor);
        }
    }

    public IntValue getBColor() {
        return new IntValue(position, bColor);
    }

    public void setBColor(IDataValue newColor) throws IncompatibleMethodArgumentException {
        if (isNewColorValueValid(newColor)) {
            bColor = ((IntValue) newColor).value();
        } else {
            throw new IncompatibleMethodArgumentException(this, newColor);
        }
    }
}
