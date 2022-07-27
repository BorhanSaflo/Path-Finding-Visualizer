import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.Color;

public class Node extends JPanel {

    private int x;
    private int y;
    private int nodeWidth;
    private int nodeHeight;

    private int gCost;
    private int hCost;
    private int fCost;
    private Node parent;

    private Color color;
    private Shape shape;
    private State state;

    public Node(int x, int y, int nodeWidth, int nodeHeight) {
        this.state = State.UNVISITED;
        this.x = x;
        this.y = y;
        this.nodeWidth = nodeWidth;
        this.nodeHeight = nodeHeight;
        this.gCost = 0;
        this.hCost = 0;
        this.fCost = 0;
        this.parent = null;
        this.color = Color.BLACK;
        this.shape = new Rectangle2D.Double(x, y, nodeWidth, nodeHeight);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.draw(shape);
        if (state == State.WALL) {
            g2.setColor(Color.BLACK);
            g2.fill(shape);
        } else if (state == State.START) {
            g2.setColor(Color.BLUE);
            g2.fill(shape);
        } else if (state == State.END) {
            g2.setColor(Color.RED);
            g2.fill(shape);
        } else if (state == State.OPEN) {
            g2.setColor(Color.GREEN);
            g2.fill(shape);
        } else if (state == State.CLOSED) {
            g2.setColor(Color.YELLOW);
            g2.fill(shape);
        } else if (state == State.UNVISITED) {
            g2.setColor(Color.WHITE);
            g2.fill(shape);
        } else if (state == State.PATH) {
            g2.setColor(Color.ORANGE);
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

    public int getWidth() {
        return nodeWidth;
    }

    public void setWidth(int width) {
        this.nodeWidth = width;
    }

    public int getHeight() {
        return nodeHeight;
    }

    public void setHeight(int height) {
        this.nodeHeight = height;
    }

    public int getGCost() {
        return gCost;
    }

    public void setGCost(int gCost) {
        this.gCost = gCost;
    }

    public int getHCost() {
        return hCost;
    }

    public void setHCost(int hCost) {
        this.hCost = hCost;
    }

    public int getFCost() {
        return fCost;
    }

    public void setFCost(int fCost) {
        this.fCost = fCost;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
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
