# GraphLibrary

This repository contains classes and algorithms for solving grid related competitive programming problems. The [examples](src/main/kotlin/examples) folder contains code solutions using this graphLibraryPackage to solve various problems. 

## The graph class
This is the most generic class, supporting all graph creation of any datatype. Any new node is given an ID upon creation, which is used to build an adjacency list. If your graph already consists of interger nodes, the IntGraph class can be used instead for increased performence.
Nodes can be connected one directionaly with .addEdge(node1, node2) or bidirectionally with .connect(node1, node2). Once the graph is build, you may use one of the graph traversal classes. Example usage:

```
fun main() {
    // --- Example Graph Definition ---
    val nNodes = 5 // Number of nodes (e.g., 0, 1, 2, 3, 4)
    // val mEdges = 7 // Number of edges (we'll add them directly, so m is implicit)
    val sStartNode = 0 // Source node for Dijkstra's
    val g = Graph()
    // Add nodes (explicitly, or Graph can add them implicitly on addEdge)
    // Nodes are 0-indexed from 0 to nNodes-1
    for (i in 0 until nNodes) {
        g.addNode(i)
    }

    // Add edges: (u, v, weight)
    g.addEdge(0, 1, 10.0)
    g.addEdge(0, 2, 3.0)
    g.addEdge(1, 3, 2.0)
    g.addEdge(2, 1, 4.0) // Node 2 can reach Node 1 with weight 4
    g.addEdge(2, 3, 8.0)
    g.addEdge(2, 4, 2.0)
    g.addEdge(3, 4, 5.0)
    // Example: Add a disconnected node or a node only reachable via others
    g.addNode(5) // Node 5 is not connected from startNode 0

    // --- Run Dijkstra's Algorithm ---
    val dijkstraRunner = Dijkstra(g.getAdjacencyList())
    dijkstraRunner.dijkstra(sStartNode)
    val distance = dijkstraRunner.distances

    // --- Querying and Printing Results ---
    // Let's define a list of goal nodes to query
    // Or query all nodes
    println("Shortest paths from source node $sStartNode:")

    // Iterate through all nodes known to the graph or just 0 to nNodes-1
    // g.getAllNodes() will include node 5.
    // If we only care about the initially "intended" nodes 0 to nNodes-1, loop up to nNodes.
    val queryNodes = (0 until nNodes).toList() + listOf(5) // Example: Query original nodes + node 5

    for (goal in queryNodes) {
        val distValue = distance[goal]
        if (distValue == null || distValue == Double.POSITIVE_INFINITY) {
            println("To node $goal: Impossible")
        } else {
            println("To node $goal: ${distValue.toInt()}")
        }
    }

    // Example: Querying a node not in the graph at all (Dijkstra's distances map won't have it)
    val nonExistentNode = 10
    val distToNonExistent = distance[nonExistentNode]
     if (distToNonExistent == null || distToNonExistent == Double.POSITIVE_INFINITY) {
        println("To node $nonExistentNode: Impossible (or node does not exist)")
    } else {
        println("To node $nonExistentNode: ${distToNonExistent.toInt()}")
    }
}
```
