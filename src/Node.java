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
    private Color borderColor;
    private Shape shape;
    private State state;

    public Node(int x, int y, int nodeWidth, int nodeHeight) {
        this.state = State.UNVISITED;
        this.x = x / nodeWidth;
        this.y = y / nodeHeight;
        this.nodeWidth = nodeWidth;
        this.nodeHeight = nodeHeight;
        this.gCost = 0;
        this.hCost = 0;
        this.fCost = 0;
        this.parent = null;
        this.borderColor = new Color(178, 178, 178);
        this.shape = new Rectangle2D.Double(x, y, nodeWidth, nodeHeight);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        switch (state) {
            case UNVISITED:
                g2.setColor(new Color(242, 242, 242));
                break;
            case CLOSED:
                g2.setColor(new Color(220, 220, 220));
                break;
            case START:
                g2.setColor(new Color(99, 249, 0));
                break;
            case END:
                g2.setColor(new Color(254, 41, 27));
                break;
            case PATH:
                g2.setColor(new Color(255, 199, 0));
                break;
            case WALL:
                g2.setColor(new Color(30, 33, 36));
                break;
        }
        g2.fill(shape);
        if (state != State.WALL) {
            g2.setColor(borderColor);
            g2.draw(shape);
        }
    }

    public boolean contains(int x, int y) {
        return shape.contains(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return nodeWidth;
    }

    public int getHeight() {
        return nodeHeight;
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

    public void setBorderColor(Color color) {
        this.borderColor = color;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
