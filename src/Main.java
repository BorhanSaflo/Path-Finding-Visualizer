import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class Main extends JFrame {

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final int SIDE_PANEL_WIDTH = 150;
    private static final int NODE_WIDTH = 25;
    private static final int NODE_HEIGHT = 25;
    private static final int GRID_WIDTH = (WINDOW_WIDTH - SIDE_PANEL_WIDTH) / NODE_WIDTH;
    private static final int GRID_HEIGHT = (WINDOW_HEIGHT - NODE_HEIGHT) / NODE_HEIGHT;

    public Main() {
        this.setTitle("Path Finding Visualizer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);

        // Grid
        Grid grid = new Grid(GRID_WIDTH, GRID_HEIGHT, NODE_WIDTH, NODE_HEIGHT);
        this.add(grid, BorderLayout.CENTER);

        // Side Panel
        JPanel sidePanel = new JPanel();
        sidePanel.setPreferredSize(new java.awt.Dimension(SIDE_PANEL_WIDTH, WINDOW_HEIGHT));
        sidePanel.setBackground(new Color(23, 35, 51));
        this.add(sidePanel, BorderLayout.EAST);

        // Controls Container
        JPanel controlsContainer = new JPanel();
        controlsContainer.setPreferredSize(new java.awt.Dimension(SIDE_PANEL_WIDTH - 20, (WINDOW_HEIGHT / 3) - 50));
        controlsContainer.setOpaque(false);
        controlsContainer.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE),
                "Controls", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP,
                new java.awt.Font("Serif", java.awt.Font.BOLD, 12), Color.WHITE));
        JButton visualizeButton = new JButton("Visualize");
        controlsContainer.add(visualizeButton);
        sidePanel.add(controlsContainer);

        // Nodes Container
        JPanel nodesContainer = new JPanel(new GridLayout(4, 1));
        nodesContainer.setPreferredSize(new java.awt.Dimension(SIDE_PANEL_WIDTH - 20, WINDOW_HEIGHT / 3));
        JButton startButton = new JButton("Start");
        JButton endButton = new JButton("End");
        JButton borderButton = new JButton("Wall");
        nodesContainer.add(startButton);
        nodesContainer.add(endButton);
        nodesContainer.add(borderButton);
        nodesContainer.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE),
                "Nodes", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP,
                new java.awt.Font("Serif", java.awt.Font.BOLD, 12), Color.WHITE));
        nodesContainer.setOpaque(false);
        sidePanel.add(nodesContainer, BorderLayout.CENTER);

        // Info Container
        JPanel infoContainer = new JPanel();
        infoContainer.setPreferredSize(new java.awt.Dimension(SIDE_PANEL_WIDTH - 20, WINDOW_HEIGHT / 5));
        JLabel pathLengthLabel = new JLabel("Path Length: " + grid.getPathLength());
        pathLengthLabel.setForeground(Color.WHITE);
        infoContainer.setOpaque(false);
        infoContainer.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE),
                "Path", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP,
                new java.awt.Font("Serif", java.awt.Font.BOLD, 12), Color.WHITE));
        infoContainer.add(pathLengthLabel);
        sidePanel.add(infoContainer, BorderLayout.CENTER);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grid.setSelectedState(State.START);
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

        visualizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grid.findPath();
                pathLengthLabel.setText("Path Length: " + grid.getPathLength());
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