import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;

public class Main extends JFrame {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final int SIDE_PANEL_WIDTH = 150;
    private static final int NODE_WIDTH = 25;
    private static final int NODE_HEIGHT = 25;
    private static final int GRID_WIDTH = ((WINDOW_WIDTH - SIDE_PANEL_WIDTH) / NODE_WIDTH) - 1;
    private static final int GRID_HEIGHT = (WINDOW_HEIGHT / NODE_HEIGHT) - 3;
    private JLabel pathLengthLabel;
    private JButton selectedButton;

    public Main() {
        this.setTitle("Path Finding Visualizer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        try {
            this.setIconImage(ImageIO.read(new File("src/Logo.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Grid
        Grid grid = new Grid(GRID_WIDTH, GRID_HEIGHT, NODE_WIDTH, NODE_HEIGHT);
        this.add(grid, BorderLayout.CENTER);

        // Side Panel
        JPanel sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(SIDE_PANEL_WIDTH, WINDOW_HEIGHT));
        sidePanel.setBackground(new Color(30, 33, 36));
        this.add(sidePanel, BorderLayout.EAST);

        // Controls Container
        String[] actions = { "Visualize Path", "Generate Maze", "Clear Path", "Reset Grid" };
        JPanel actionsContainer = createContainer("Actions", true, actions.length);
        JButton[] actionButtons = new JButton[actions.length];
        for (int i = 0; i < actions.length; i++) {
            actionButtons[i] = createButton(actions[i]);
            actionButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!grid.isRunning()) {
                        updatePathLengthLabel(0);
                        if (e.getActionCommand().equals("Visualize Path")) {
                            grid.findPath();
                            updatePathLengthLabel(grid.getPathLength());
                        } else if (e.getActionCommand().equals("Generate Maze")) {
                            grid.generateMaze();
                        } else if (e.getActionCommand().equals("Clear Path")) {
                            grid.clear();
                        } else if (e.getActionCommand().equals("Reset Grid")) {
                            grid.reset();
                        }
                    }
                }
            });
            actionsContainer.add(actionButtons[i]);
        }
        sidePanel.add(actionsContainer);

        // Nodes Container
        String[] nodes = { "Start", "End", "Wall" };
        JPanel nodesContainer = createContainer("Nodes", true, nodes.length);
        JButton[] nodeButtons = new JButton[actions.length];
        for (int i = 0; i < nodes.length; i++) {
            nodeButtons[i] = createButton(nodes[i]);
            nodeButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    grid.setSelectedState(State.valueOf(e.getActionCommand().toUpperCase()));
                    highlightSelectedButton((JButton) e.getSource());
                }
            });
            nodesContainer.add(nodeButtons[i]);
        }
        highlightSelectedButton(nodeButtons[0]);
        sidePanel.add(nodesContainer);

        // Path Stats Container
        JPanel infoContainer = createContainer("Path Stats", false, 0);
        pathLengthLabel = new JLabel("Path Length: " + grid.getPathLength());
        pathLengthLabel.setForeground(Color.WHITE);
        infoContainer.add(pathLengthLabel);
        sidePanel.add(infoContainer);
    }

    public JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setMargin(new Insets(0, -20, 0, -20));
        return button;
    }

    public JPanel createContainer(String title, boolean gridLayout, int buttonsLength) {
        JPanel container = new JPanel();
        if (gridLayout) {
            container.setLayout(new GridLayout(buttonsLength, 1, 0, 10));
            container.setPreferredSize(new Dimension(SIDE_PANEL_WIDTH - 20, 50 * buttonsLength));
        } else {
            container.setPreferredSize(new Dimension(SIDE_PANEL_WIDTH - 20, (WINDOW_HEIGHT / 4)));
        }
        container.setBorder(new CompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE),
                        title, javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP,
                        new Font("Dialog", Font.BOLD, 12), Color.WHITE),
                BorderFactory.createEmptyBorder(2, 5, 5, 5)));
        container.setOpaque(false);
        return container;
    }

    public void updatePathLengthLabel(int pathLength) {
        pathLengthLabel.setText("Path Length: " + pathLength);
    }

    public void highlightSelectedButton(JButton button) {
        if (selectedButton != null) {
            selectedButton.setBorderPainted(false);
        }
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
        selectedButton = button;
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