package examples

import graphClasses.Graph
import graphClasses.IntGraph
import graphGraphics.visualizeSearch


fun main() {
    // --- Example Graph Definition ---
    val graph = Graph()
    graph.addWeightedEdge(0, 1, 10.0)
    graph.addWeightedEdge(0, 2, 3.0)
    graph.addWeightedEdge(1, 3, 2.0)
    graph.addWeightedEdge(2, 1, 4.0)
    graph.addWeightedEdge(2, 3, 8.0)
    graph.addWeightedEdge(2, 4, 2.0)
    graph.addWeightedEdge(3, 4, 5.0)

    graph.addNode(5) // Adding an isolated node is also possible

    val startNode = 0
    val targetNode = 3
    graph.dijkstra(startNode, targetNode) // Provide a goal target node to stop the search when the target is found
    val nodes: List<Int> =
        graph.nodes().map { it as Int } // Nodes are of type Any and must therefore be casted to Int
    println("Shortest paths from source node $startNode:")
    nodes.forEach { node ->
        val distValue = graph.weightedDistanceTo(node)
        val path = graph.getPath(node)
        println("To node $node: Distance $distValue Path: ${if (distValue < Int.MAX_VALUE) path else null}")
    }
    /* Output:
        Shortest paths from source node 0:
        Distance to node 0: 0.0 Path: [0]
        Distance to node 1: 7.0 Path: [0, 2, 1]
        Distance to node 2: 3.0 Path: [0, 2]
        Distance to node 3: 9.0 Path: [0, 2, 1, 3]
        Distance to node 4: 5.0 Path: [0, 2, 4]
        Distance to node 5: Infinity Path: null
    */


    /* --- Example IntGraph Definition ---
         * An IntGraph can be defined the same way as the Graph same way as above,
         * but it can also be initialized with a size, because the nodes are integers values from 0 to n-1.
    */

    val n = graph.size()
    val intGraph = IntGraph(n)
    // Add the same edges as the above Graph
    graph.nodes().forEach { fromNode ->
        graph.getWeightedEdges(fromNode).forEach { edge ->
            val weight = edge.first
            val toNode = edge.second as Int // Cast type Any to Int
            intGraph.addWeightedEdge(fromNode as Int, toNode, weight)
        }
    }
    intGraph.dijkstra(startNode, targetNode)
    val intNodes: List<Int> = intGraph.nodes()
    println("Shortest paths from source node $startNode:")
    intNodes.forEach { node ->
        val distValue = intGraph.weightedDistanceTo(node)
        val path = intGraph.getPath(node)
        println("To node $node: Distance $distValue Path: ${if (distValue < Int.MAX_VALUE) path else null}")
    }
    // Outputs the same as the code above

    // Visualize the graph using brunomnsilva's JavaFXSmartGraph: https://github.com/brunomnsilva/JavaFXSmartGraph
    graph.visualizeSearch( // Or use: intGraph.visualizeSearch(
        screenTitle = "Grid example visualizing",
        animationTicTimeOverride = 500.0,
        startPaused = false,
        closeOnEnd = false
    )
}
