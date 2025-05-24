package examples

import graphClasses.Dijkstra
import graphClasses.Graph


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
        println("To node $goalNodeId: ${distValue.toInt()}")
    }
    /* Output:
    To node 0: 0
    To node 1: 7
    To node 2: 3
    To node 3: 9
    To node 4: 5
    To node 5: 2147483647 (positive infinity, as node 5 is isolated)
     */
}