package examples

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