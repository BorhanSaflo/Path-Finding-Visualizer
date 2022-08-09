import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;

public class Grid extends JPanel {
    private int gridWidth;
    private int gridHeight;
    private Node[][] grid;
    private int[][] maze;
    private Node start;
    private Node end;
    private Node lastSelectedNode;
    private State selectedState;
    private boolean isRunning;
    AStar aStar;

    public Grid(int width, int height, int nodeWidth, int nodeHeight) {
        this.selectedState = State.START;
        this.gridWidth = width;
        this.gridHeight = height;
        this.maze = new int[gridWidth][gridHeight];
        this.grid = new Node[gridWidth][gridHeight];
        this.isRunning = false;
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                grid[i][j] = new Node(i * nodeWidth, j * nodeHeight, nodeWidth, nodeHeight, State.EMPTY);
            }
        }
        setStartAndEndNodes();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                findNode(me.getX(), me.getY());
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                findNode(me.getX(), me.getY());
            }
        });
    }

    public void findPath() {
        isRunning = true;
        clear();
        aStar = new AStar(grid, start, end, gridWidth, gridHeight);
        aStar.findPath();
        animateClosedNodes();
    }

    public void setStartAndEndNodes() {
        this.start = grid[1][1];
        this.start.setState(State.START);
        this.end = grid[gridWidth - 2][gridHeight - 2];
        this.end.setState(State.END);
    }

    public void clear() {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                if (grid[i][j].getState() == State.CLOSED || grid[i][j].getState() == State.PATH) {
                    grid[i][j].setState(State.EMPTY);
                }
            }
        }
        repaint();
    }

    public void reset() {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                if (grid[i][j].getState() != State.EMPTY) {
                    grid[i][j].setState(State.EMPTY);
                }
            }
        }
        setStartAndEndNodes();
        repaint();
    }

    public void findNode(int x, int y) {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                if (grid[i][j].contains(x, y)) {
                    changeNodeState(grid[i][j]);
                }
            }
        }
    }

    public void changeNodeState(Node node) {
        clear();
        if (selectedState == State.START) {
            this.start.setState(State.EMPTY);
            this.start = node;
            node.setState(State.START);
        } else if (selectedState == State.END) {
            this.end.setState(State.EMPTY);
            this.end = node;
            node.setState(State.END);
        } else if (selectedState == State.WALL && node != lastSelectedNode) {
            if (node.getState() == State.EMPTY) {
                node.setState(State.WALL);
            } else if (node.getState() == State.WALL) {
                node.setState(State.EMPTY);
            }
            lastSelectedNode = node;
        }
        repaint();
    }

    public void generateMaze() {
        isRunning = true;
        reset();

        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                maze[i][j] = 1;
            }
        }

        Random rand = new Random();

        int randomRow = rand.nextInt(gridWidth);
        while (randomRow % 2 == 0) {
            randomRow = rand.nextInt(gridWidth);
        }
        // Generate random c
        int randomCol = rand.nextInt(gridHeight);
        while (randomCol % 2 == 0) {
            randomCol = rand.nextInt(gridHeight);
        }

        maze[randomRow][randomCol] = 0;
        mazeRecursion(randomRow, randomCol);

        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                if (maze[i][j] == 1) {
                    grid[i][j].setState(State.WALL);
                }
            }
        }
        setStartAndEndNodes();
        repaint();
        isRunning = false;
    }

    public void mazeRecursion(int row, int col) {
        Integer[] randomDirections = generateRandomDirections();
        for (int direction : randomDirections) {
            switch (direction) {
                // North
                case 1:
                    // Check if 2 nodes north is inbounds
                    if (row - 2 <= 0)
                        continue;
                    if (maze[row - 2][col] != 0) {
                        maze[row - 2][col] = 0;
                        maze[row - 1][col] = 0;
                        mazeRecursion(row - 2, col);
                    }
                    break;
                // East
                case 2:
                    // Check if 2 nodes east is inbounds
                    if (col + 2 >= gridHeight - 1)
                        continue;
                    if (maze[row][col + 2] != 0) {
                        maze[row][col + 2] = 0;
                        maze[row][col + 1] = 0;
                        mazeRecursion(row, col + 2);
                    }
                    break;
                case 3: // South
                    // Check if 2 nodes south is inbounds
                    if (row + 2 >= gridWidth - 1)
                        continue;
                    if (maze[row + 2][col] != 0) {
                        maze[row + 2][col] = 0;
                        maze[row + 1][col] = 0;
                        mazeRecursion(row + 2, col);
                    }
                    break;
                // West
                case 4:
                    // Check if 2 nodes west is inbounds
                    if (col - 2 <= 0)
                        continue;
                    if (maze[row][col - 2] != 0) {
                        maze[row][col - 2] = 0;
                        maze[row][col - 1] = 0;
                        mazeRecursion(row, col - 2);
                    }
                    break;
            }
        }
    }

    public Integer[] generateRandomDirections() {
        ArrayList<Integer> randoms = new ArrayList<Integer>();
        for (int i = 0; i < 4; i++)
            randoms.add(i + 1);
        Collections.shuffle(randoms);

        return randoms.toArray(new Integer[4]);
    }

    public void animatePathNodes() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (aStar.getPath().isEmpty()) {
                    borderBlinkAnimation(true);
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
                    if (aStar.getPath().isEmpty()) {
                        borderBlinkAnimation(false);
                    } else {
                        animatePathNodes();
                    }
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

    public void changeBorderColor(Color color) {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                grid[i][j].setBorderColor(color);
            }
        }
        repaint();
    }

    public void borderBlinkAnimation(boolean found) {
        try {
            for (int i = 0; i < 3; i++) {
                changeBorderColor(found ? new Color(26, 229, 0) : new Color(229, 45, 0));
                Thread.sleep(300);
                changeBorderColor(new Color(178, 178, 178));
                Thread.sleep(300);
            }
            isRunning = false;
        } catch (InterruptedException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
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

    public boolean isRunning() {
        return isRunning;
    }
}
