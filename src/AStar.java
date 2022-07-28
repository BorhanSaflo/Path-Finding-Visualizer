import java.util.ArrayList;
import java.util.Collections;

//A* algorithm
public class AStar {
    private Node[][] grid;
    private int gridWidth, gridHeight;
    private Node start, end;
    private ArrayList<Node> openList, closedList;
    private ArrayList<Node> path;
    private boolean pathFound;
    private int pathLength;

    public AStar(Node[][] grid, Node start, Node end, int gridWidth, int gridHeight) {
        this.grid = grid;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.start = start;
        this.end = end;
        this.openList = new ArrayList<Node>();
        this.closedList = new ArrayList<Node>();
        this.path = new ArrayList<Node>();
        this.pathFound = false;
        this.pathLength = 0;
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
                if (neighbor.getState() == State.WALL || closedList.contains(neighbor)) {
                    continue;
                }

                // Create the f, g, and h values
                neighbor.setGCost(current.getGCost() + 1);
                neighbor.setHCost((int) Math.pow(neighbor.getX() - end.getX(), 2)
                        + (int) Math.pow(neighbor.getY() - end.getY(), 2));
                neighbor.setFCost(neighbor.getGCost() + neighbor.getHCost());

                // Child is already in openList
                if (openList.contains(neighbor) && current.getGCost() < neighbor.getGCost()) {
                    continue;
                }

                openList.add(neighbor);
                neighbor.setParent(current);
            }
        }

        // if path is found, get the path from start to end
        if (pathFound) {
            Node current = end.getParent();
            while (!current.equals(start)) {
                path.add(current);
                current = current.getParent();
            }
            Collections.reverse(path);
            pathLength = path.size();
        }
    }

    // get node neighbors that are not walls and not outside the grid
    public ArrayList<Node> getNeighbors(Node node) {
        ArrayList<Node> neighbors = new ArrayList<Node>();
        int x = node.getX();
        int y = node.getY();
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

    public ArrayList<Node> getPath() {
        return path;
    }

    public ArrayList<Node> getClosedList() {
        return closedList;
    }

    public int getPathLength() {
        return pathLength;
    }
}
