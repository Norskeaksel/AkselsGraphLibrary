# GraphLibrary

This repository contains classes and algorithms for solving grid related competitive programming problems. The [examples](src/main/kotlin/examples) folder contains code solutions using this graphLibraryPackage to solve various problems. 

## The graph class
This is the most generic class, supporting all graph creation of any datatype. Any new node is given an ID upon creation,
which is used to build an adjacency list. If your graph already consists of interger nodes, the IntGraph class can be used instead for increased performence.
Nodes can be connected one directionaly with .addEdge(node1, node2) or bidirectionally with .connect(node1, node2).
Once the graph is build, you may use one of the graph traversal classes. [Example usage:](src/main/kotlin/examples/GraphExample.kt)

```kotlin
import graphClasses.Dijkstra
import graphClasses.Graph
import graphClasses.getPath


fun main() {
    // --- Example Graph Definition ---
    val g = Graph()
    g.addEdge(0, 1, 10.0)
    g.addEdge(0, 2, 3.0)
    g.addEdge(1, 3, 2.0)
    g.addEdge(2, 1, 4.0)
    g.addEdge(2, 3, 8.0)
    g.addEdge(2, 4, 2.0)
    g.addEdge(3, 4, 5.0)

    g.addNode(5) // Adding an isolated node is also possible

    val dijkstraRunner = Dijkstra(g)
    val startNode = 0
    dijkstraRunner.dijkstra(startNode)
    val distance = dijkstraRunner.distances

    println("Shortest paths from source node $startNode:")
    for (goalNodeId in 0 until g.size()) {
        val distValue = distance[goalNodeId]
        val path = getPath(goalNodeId, dijkstraRunner.parents)
        println("To node $goalNodeId: ${distValue.toInt()}. Path: ${if (distValue < Int.MAX_VALUE) path else null}")
    }
    /* Output:
    Shortest paths from source node 0:
    Distance to node 0: 0. Path: [0]
    Distance to node 1: 7. Path: [0, 2, 1]
    Distance to node 2: 3. Path: [0, 2]
    Distance to node 3: 9. Path: [0, 2, 1, 3]
    Distance to node 4: 5. Path: [0, 2, 4]
    Distance to node 5: 2147483647. Path: null
     */
}
```

## The Grid class
The Grid class is a specialized graph class. It uses the data class ```Tile(val x: Int, val y: Int, var data: Any? = null)```
to represent nodes of any datatype, but each node also have x and y coordinates.
The grid can be created with a width and height, or by passing a list of strings.
The grid can be traversed using the same algorithms as the graph class,
but it also has some additional methods for connecting the grid without explicitly adding edges. [Example usage:](src/main/kotlin/examples/GridExample.kt)

```kotlin
import graphClasses.BFS
import graphClasses.Grid

fun main(){
    // --- Example Grid Definition ---
    val width = 3
    val height = 3
    val grid = Grid(width, height)
    grid.connectGridDefault()

    val bfs = BFS(grid)
    bfs.bfsIterative(0)
    val distance = bfs.distances
    for (goalNodeId in 0 until width * height) {
        val distValue = distance[goalNodeId]
        val node = grid.id2Node(goalNodeId)
        println("To node $node: ${distValue.toInt()}")
    }
    /* Output:
    To node Tile(x=0, y=0, data=null): 0 (no specific node data was set)
    To node Tile(x=1, y=0, data=null): 1
    To node Tile(x=2, y=0, data=null): 2
    To node Tile(x=0, y=1, data=null): 1
    To node Tile(x=1, y=1, data=null): 2
    To node Tile(x=2, y=1, data=null): 3
    To node Tile(x=0, y=2, data=null): 2
    To node Tile(x=1, y=2, data=null): 3
    To node Tile(x=2, y=2, data=null): 4
     */
}
```

## Graphics
This library has also been used to make grid visualizations, which can be checked out [here](https://github.com/Norskeaksel/GridGraphics/).