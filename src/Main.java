import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class Main extends JFrame {

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final int BUTTONS_CONTAINER_HEIGHT = 50;
    private static final int CELL_WIDTH = 50;
    private static final int CELL_HEIGHT = 50;
    private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 10;

    public Main() {
        this.setTitle("Path Finding Visualizer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);

        JPanel buttonsContainer = new JPanel();
        buttonsContainer.setPreferredSize(new java.awt.Dimension(WINDOW_WIDTH, BUTTONS_CONTAINER_HEIGHT));
        buttonsContainer.setBackground(Color.DARK_GRAY);

        JButton startButton = new JButton("Start");
        JButton endButton = new JButton("End");
        JButton borderButton = new JButton("Wall");
        buttonsContainer.add(startButton);
        buttonsContainer.add(endButton);
        buttonsContainer.add(borderButton);

        this.add(buttonsContainer, BorderLayout.SOUTH);

        Grid grid = new Grid(GRID_WIDTH, GRID_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
        this.add(grid, BorderLayout.CENTER);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grid.setSelectedState(State.START);
                grid.findPath();
            }
        });
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grid.setSelectedState(State.END);
            }
        });
        borderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grid.setSelectedState(State.WALL);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }
}