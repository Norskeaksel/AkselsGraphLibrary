package examples

import graphClasses.Dijkstra
import graphClasses.Graph
import graphClasses.IntGraph
import graphClasses.getPath


fun main() {
    // --- Example Graph Definition ---
    val graph = Graph()
    graph.addEdge(0, 1, 10.0)
    graph.addEdge(0, 2, 3.0)
    graph.addEdge(1, 3, 2.0)
    graph.addEdge(2, 1, 4.0)
    graph.addEdge(2, 3, 8.0)
    graph.addEdge(2, 4, 2.0)
    graph.addEdge(3, 4, 5.0)

    graph.addNode(5) // Adding an isolated node is also possible

    val graphDijkstra = Dijkstra(graph)
    val startNode = 0
    graphDijkstra.dijkstra(startNode)
    val graphDistances = graphDijkstra.distances

    println("Shortest paths from source node $startNode:")
    repeat(graph.size()) { id ->
        val distValue = graphDistances[id]
        val path = getPath(id, graphDijkstra.parents)
        println("To node $id: Distance ${distValue.toInt()}. Path: ${if (distValue < Int.MAX_VALUE) path else null}")
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

    /* --- Example IntGraph Definition ---
     * An intGraph can be defined similar to the Graph same way as above, but it can also be initialized with a size,
     * because the nodes are integers values from 0 to n-1.
     */
    val n = graph.size()
    val intGraph = IntGraph(n)
    graph.getAdjacencyList().forEachIndexed { nodeId, edges ->
        edges.forEach { edge -> // Pair(weight, destination node ID)
            intGraph.addEdge(nodeId, edge.second, edge.first)
        }
    }
    val intGraphDijkstra = Dijkstra(intGraph)
    intGraphDijkstra.dijkstra(startNode)
    val intGraphDistances = intGraphDijkstra.distances
    println("Shortest paths from source node $startNode:")
    repeat(n) { id ->
        val distValue = intGraphDistances[id]
        val path = getPath(id, intGraphDijkstra.parents)
        println("To node $id: Distance ${distValue.toInt()}. Path: ${if (distValue < Int.MAX_VALUE) path else null}")
    }
    // Outputs the same as the code above
}