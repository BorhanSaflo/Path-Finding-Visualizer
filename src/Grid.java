import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Grid extends JPanel {

    private int gridWidth;
    private int gridHeight;
    private int nodeWidth;
    private int nodeHeight;
    private Node[][] grid;
    private Node start;
    private Node end;
    private State selectedState;
    AStar aStar = null;

    public Grid(int width, int height, int nodeWidth, int nodeHeight) {
        this.selectedState = State.START;
        this.gridWidth = width;
        this.gridHeight = height;
        this.nodeWidth = nodeWidth;
        this.nodeHeight = nodeHeight;
        this.grid = new Node[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new Node(i * nodeWidth, j * nodeHeight, nodeWidth, nodeHeight);
            }
        }
        this.start = grid[0][0];
        grid[0][0].setState(State.START);
        this.end = grid[width - 1][height - 1];
        grid[width - 1][height - 1].setState(State.END);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                checkCell(me.getX(), me.getY());
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                checkCell(me.getX(), me.getY());
            }
        });
    }

    public void findPath() {
        clearPath();
        aStar = new AStar(grid, start, end, gridWidth, gridHeight, nodeWidth, nodeHeight);
        aStar.findPath();
        repaint();
    }

    public void clearPath() {
        if (aStar != null) {
            for (Node node : aStar.getPath()) {
                if (node.getState() == State.PATH) {
                    node.setState(State.UNVISITED);
                }
            }
        }
        repaint();
    }

    public void checkCell(int x, int y) {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                if (grid[i][j].contains(x, y)) {
                    changeCellState(i, j);
                }
            }
        }
    }

    public void changeCellState(int x, int y) {
        Node node = grid[x][y];
        clearPath();
        if (selectedState == State.START) {
            this.start.setState(State.UNVISITED);
            this.start = node;
            node.setState(State.START);
        } else if (selectedState == State.END) {
            this.end.setState(State.UNVISITED);
            this.end = node;
            node.setState(State.END);
        } else if (selectedState == State.WALL && node.getState() == State.UNVISITED) {
            node.setState(State.WALL);
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                grid[i][j].paintComponent(g);
            }
        }
    }

    public void setSelectedState(State currentState) {
        this.selectedState = currentState;
    }

    public Node getCell(int x, int y) {
        return grid[x][y];
    }
}
