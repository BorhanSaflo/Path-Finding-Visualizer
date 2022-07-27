import java.util.ArrayList;
import java.util.Collections;

//a star algorithm
public class AStar {
    private Node[][] grid;
    private int gridWidth, gridHeight;
    private int nodeWidth, nodeHeight;
    private Node start, end;
    private ArrayList<Node> openList, closedList;
    private ArrayList<Node> path;
    private boolean pathFound;
    private int pathLength;
    private int pathCost;

    public AStar(Node[][] grid, Node start, Node end, int gridWidth, int gridHeight, int nodeWidth, int nodeHeight) {
        this.grid = grid;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.nodeWidth = nodeWidth;
        this.nodeHeight = nodeHeight;
        this.start = start;
        this.end = end;
        this.openList = new ArrayList<Node>();
        this.closedList = new ArrayList<Node>();
        this.path = new ArrayList<Node>();
        this.pathFound = false;
        this.pathLength = 0;
        this.pathCost = 0;
    }

    // get node neighbors that are not walls and not outside the grid
    public ArrayList<Node> getNeighbors(Node node) {
        ArrayList<Node> neighbors = new ArrayList<Node>();
        int x = node.getX() / nodeWidth;
        int y = node.getY() / nodeHeight;
        if (x > 0) {
            neighbors.add(grid[x - 1][y]);
        }
        if (x < gridWidth - 1) {
            neighbors.add(grid[x + 1][y]);
        }
        if (y > 0) {
            neighbors.add(grid[x][y - 1]);
        }
        if (y < gridHeight - 1) {
            neighbors.add(grid[x][y + 1]);
        }
        return neighbors;
    }

    // get the node with the lowest f value
    public Node getLowestF() {
        Node lowest = openList.get(0);
        for (Node node : openList) {
            if (node.getFCost() < lowest.getFCost()) {
                lowest = node;
            }
        }
        return lowest;
    }

    // get the node with the lowest g value
    public Node getLowestG() {
        Node lowest = openList.get(0);
        for (Node node : openList) {
            if (node.getGCost() < lowest.getGCost()) {
                lowest = node;
            }
        }
        return lowest;
    }

    // print g costs of each node
    public void printGCosts() {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                System.out.print(grid[i][j].getGCost() + " ");
            }
            System.out.println();
        }
    }

    // initialize h costs of each node
    public void initHCosts() {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                grid[i][j]
                        .setHCost(Math.abs(grid[i][j].getX() - end.getX()) + Math.abs(grid[i][j].getY() - end.getY()));
            }
        }
        printHCosts();
    }

    // print h costs
    public void printHCosts() {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                System.out.print(grid[i][j].getHCost() + " ");
            }
            System.out.println();
        }
    }

    //print f costs
    public void printFCosts() {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                System.out.print(grid[i][j].getFCost() + " ");
            }
            System.out.println();
        }
    }

    // get the node with the lowest h value
    public Node getLowestH() {
        Node lowest = openList.get(0);
        for (Node node : openList) {
            if (node.getHCost() < lowest.getHCost()) {
                lowest = node;
            }
        }
        return lowest;
    }

    // find the path from start to end and update the state of the node at each step
    public void findPath() {
        openList.add(start);
        while (!openList.isEmpty()) {
            Node current = getLowestF();
            if (current.equals(end)) {
                pathFound = true;
                break;
            }
            openList.remove(current);
            closedList.add(current);

            for (Node neighbor : getNeighbors(current)) {
                if (neighbor.getState() == State.WALL) {
                    continue;
                }
                if (closedList.contains(neighbor)) {
                    continue;
                }
                int tempGCost = current.getGCost() + 1;

                if (!openList.contains(neighbor)) {
                    openList.add(neighbor);
                }

                else if (tempGCost >= neighbor.getGCost()) {
                    continue;
                }
                neighbor.setParent(current);
                neighbor.setGCost(tempGCost);
                neighbor.setFCost(neighbor.getGCost() + neighbor.getHCost());
            }
        }

        // if path is found, get the path from start to end
        if (pathFound) {
        Node current = end;
        while (!current.equals(start)) {
        path.add(current);
        current = current.getParent();
        }
        path.add(start);
        Collections.reverse(path);
        pathLength = path.size();
        pathCost = end.getGCost();
        }

        // update the state of the nodes in the path
        for (Node node : path) {
        node.setState(State.PATH);
        }

        //printGCosts();

    }

}
