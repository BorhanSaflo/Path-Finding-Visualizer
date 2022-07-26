import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Grid extends JPanel {

    private int width;
    private int height;
    private Cell[][] grid;
    private State selectedState;

    public Grid(int width, int height, int cellWidth, int cellHeight) {
        this.selectedState = State.START;
        this.width = width;
        this.height = height;
        this.grid = new Cell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new Cell(i * cellWidth, j * cellHeight, cellWidth, cellHeight);
            }
        }

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

    public void checkCell(int x, int y) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[i][j].contains(x, y)) {
                    changeCellState(i, j);
                }
            }
        }
    }

    public void changeCellState(int x, int y) {
        if (selectedState == State.START) {
            grid[x][y].setState(State.START);
        } else if (selectedState == State.END) {
            grid[x][y].setState(State.END);
        } else if (selectedState == State.BORDER) {
            grid[x][y].setState(State.BORDER);
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j].paintComponent(g);
            }
        }
    }

    public void setSelectedState(State currentState) {
        this.selectedState = currentState;
    }
}
