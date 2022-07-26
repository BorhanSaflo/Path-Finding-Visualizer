import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.Color;

public class Cell extends JPanel {

    private int x;
    private int y;
    private Color color;
    private Shape shape;
    private State state;

    public Cell(int x, int y, int width, int height) {
        this.state = State.EMPTY;
        this.x = x;
        this.y = y;
        this.color = Color.BLACK;
        this.shape = new Rectangle2D.Double(x, y, width, height);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.draw(shape);
        if (state == State.BORDER) {
            g2.setColor(Color.BLACK);
            g2.fill(shape);
        } else if (state == State.PATH) {
            g2.setColor(Color.GREEN);
            g2.fill(shape);
        } else if (state == State.START) {
            g2.setColor(Color.BLUE);
            g2.fill(shape);
        } else if (state == State.END) {
            g2.setColor(Color.RED);
            g2.fill(shape);
        }

    }

    public boolean contains(int x, int y) {
        return shape.contains(x, y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
