import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

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
        this.start.setState(State.START);
        this.end = grid[width - 1][height - 1];
        this.end.setState(State.END);

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
        clear();
        aStar = new AStar(grid, start, end, gridWidth, gridHeight, nodeWidth, nodeHeight);
        aStar.findPath();
        animateClosedNodes();
    }

    public void clear() {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                if (grid[i][j].getState() == State.CLOSED || grid[i][j].getState() == State.PATH) {
                    grid[i][j].setState(State.UNVISITED);
                }
            }
        }
        repaint();
    }

    public void reset() {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                if (grid[i][j].getState() != State.UNVISITED) {
                    grid[i][j].setState(State.UNVISITED);
                }
            }
        }
        start = grid[0][0];
        start.setState(State.START);
        end = grid[gridWidth - 1][gridHeight - 1];
        end.setState(State.END);
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
        clear();
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

    public void animatePathNodes() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (aStar.getPath().isEmpty()) {
                    timer.cancel();
                } else {
                    Node node = aStar.getPath().remove(0);
                    node.setState(State.PATH);
                    repaint();
                }
            }
        }, 0, 40);
    }

    public void animateClosedNodes() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (aStar.getClosedList().isEmpty()) {
                    timer.cancel();
                    animatePathNodes();

                } else {
                    Node node = aStar.getClosedList().remove(0);
                    if (node.getState() != State.START && node.getState() != State.END) {
                        node.setState(State.CLOSED);
                    }
                    repaint();
                }
            }
        }, 0, 5);
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

    public int getPathLength() {
        if (aStar == null) {
            return 0;
        }
        return aStar.getPathLength();
    }
}
