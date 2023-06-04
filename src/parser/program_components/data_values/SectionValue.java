package parser.program_components.data_values;

import lexer.Position;
import parser.IDataValue;
import parser.IExpression;
import visitors.IVisitor;

import javax.swing.*;
import java.awt.*;

public record SectionValue(Position position, IExpression first, IExpression second) implements IDataValue {
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
}
