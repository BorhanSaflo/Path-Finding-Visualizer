# Path-Finding-Visualizer
A Java application that lets users create their own mazes by placing wall barriers, a start point, and an end point on a 2D grid and then watching the A* algorithm find the shortest and most efficient path through the maze.

The A* algorithm in the grid below begins at the start point (green node) and considers all adjacent nodes. After populating the list of neighboring nodes, it filters out those that are inaccessible (walls obstacles, out of bounds). The node with the lowest cost is then chosen. This process is repeated until the shortest Manhattan distance to the end point (red node) is found.

![Path Finding Visualizer 1](https://user-images.githubusercontent.com/60056206/181806482-df3a1c2f-ab26-44cb-9219-029c86f07e5d.gif)