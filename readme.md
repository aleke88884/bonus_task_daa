# MST Edge Removal and Replacement

This project demonstrates the construction of a **Minimum Spanning Tree (MST)** using **Kruskal’s algorithm**, the removal of an edge from the MST, and the process of finding a **replacement edge** to restore the graph’s connectivity.

## Overview

The program performs the following operations:

1. **Build the MST** – Uses Kruskal’s algorithm to construct the minimum spanning tree.
2. **Display the MST** – Shows all edges included in the tree and the total weight.
3. **Remove an edge** – Removes one selected edge from the MST.
4. **Show components** – Displays the connected components after the edge removal.
5. **Find a replacement edge** – Finds the minimum-weight edge that reconnects the graph.
6. **Display the new MST** – Shows the updated tree after reconnection.

## Project Structure

```
mst-edge-removal/
├── Graph.java           # Graph class implementing MST algorithms
├── MSTEdgeRemoval.java  # Main demo class
├── TestMST.java         # Detailed test class with step-by-step output
├── README.md            # This file
└── EXPLANATION.md       # In-depth explanation of the algorithm
```

## Example Output

```
=== MST Edge Removal and Replacement Demo ===

Graph created with 6 vertices and 9 edges

Step 1: Building Minimum Spanning Tree (MST)
--------------------------------------------
Original MST edges:
  (1 - 2, weight: 1)
  (1 - 3, weight: 2)
  (3 - 4, weight: 2)
  (0 - 2, weight: 3)
  (4 - 5, weight: 3)
Total MST weight: 11

Step 2: Removing edge from MST
--------------------------------------------
Removed edge: (3 - 4, weight: 2)

Step 3: Components after edge removal
--------------------------------------------
Component 1: [0, 1, 2, 3]
Component 2: [4, 5]

Step 4: Finding replacement edge
--------------------------------------------
Replacement edge found: (2 - 4, weight: 5)

Step 5: New MST after reconnection
--------------------------------------------
New MST edges:
  (1 - 2, weight: 1)
  (1 - 3, weight: 2)
  (0 - 2, weight: 3)
  (4 - 5, weight: 3)
  (2 - 4, weight: 5)
Total new MST weight: 14

✓ Graph successfully reconnected!
```

## Algorithms Used

### Kruskal’s Algorithm

* **Complexity:** O(E log E), where E = number of edges
* Uses the **Union-Find (Disjoint Set Union)** data structure to efficiently detect cycles.
* Sorts edges by weight and adds them to the MST if they do not create a cycle.

### Replacement Edge Search

* **Complexity:** O(E)
* Identifies the connected components after an edge is removed from the MST.
* Finds the smallest-weight edge connecting the two separate components.

### Union-Find (Disjoint Set Union)

* **Find:** O(α(n)) with path compression
* **Union:** O(α(n)) with union by rank
* α(n) is the inverse Ackermann function (practically constant for all real inputs)

## Modifying the Graph

You can change the sample graph in the `createSampleGraph()` method of `MSTEdgeRemoval.java`:

```java
private static Graph createSampleGraph() {
    Graph graph = new Graph(6); // Number of vertices
    
    // Add your own edges
    graph.addEdge(0, 1, 4);  // from, to, weight
    graph.addEdge(0, 2, 3);
    // ... add more edges
    
    return graph;
}
```

## Key Classes and Methods

### `Graph.java`

* `addEdge(int src, int dest, int weight)` – Adds an edge to the graph
* `kruskalMST()` – Builds the MST using Kruskal’s algorithm
* `findComponents(List<Edge> mstEdges)` – Finds connected components in the MST
* `findReplacementEdge(List<Edge> mstEdges, Edge removedEdge)` – Finds the minimal edge reconnecting the graph

### `Edge` (inner class)

* Represents a weighted edge in the graph
* Implements `Comparable` for sorting by weight

### `UnionFind` (inner class)

* Efficient structure for connectivity checks
* Uses **path compression** and **union by rank**

## Author

Created as a **bonus assignment** for the Algorithms and Data Structures course.

## License

Free to use for **educational purposes**.
