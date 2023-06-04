package parser.program_components.data_values;

import lexer.Position;
import parser.IDataValue;
import parser.IExpression;
import visitors.IVisitor;
import visitors.exceptions.IncompatibleMethodArgumentException;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class SectionValue implements IDataValue {
    private final Position position;
    private final IExpression first;
    private final IExpression second;
    private int rColor;
    private int gColor;
    private int bColor;

    public SectionValue(Position position, IExpression first, IExpression second) {
        this.position = position;
        this.first = first;
        this.second = second;
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
    public String getPrinting() {
        return "Section[" + ((IDataValue) first).getPrinting() + ", " + ((IDataValue) second).getPrinting() + "]";
    }

    @Override
    public void print() {
        System.out.println(getPrinting());
    }

    public void draw(JFrame frame) {
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g0) {
                Graphics2D g2d = (Graphics2D) g0;

                float lineWidth = 2.0f;
                g2d.setStroke(new BasicStroke(lineWidth));

                int firstX = ((DoubleValue) ((PointValue) first).x()).value().intValue();
                int firstY = ((DoubleValue) ((PointValue) first).y()).value().intValue();
                int secondX = ((DoubleValue) ((PointValue) second).x()).value().intValue();
                int secondY = ((DoubleValue) ((PointValue) second).y()).value().intValue();

                g2d.setColor(Color.RED);
                g2d.drawLine(firstX, firstY, secondX, secondY);
            }
        };
        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public Position position() {
        return position;
    }

    public IExpression first() {
        return first;
    }

    public IExpression second() {
        return second;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SectionValue) obj;
        return Objects.equals(this.position, that.position) &&
                Objects.equals(this.first, that.first) &&
                Objects.equals(this.second, that.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, first, second);
    }

    @Override
    public String toString() {
        return "SectionValue[" +
                "position=" + position + ", " +
                "first=" + first + ", " +
                "second=" + second + ']';
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
