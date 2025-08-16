package graphClasses

import com.sun.org.apache.xml.internal.security.algorithms.Algorithm
import pathfindingAlgorithms.BFS
import pathfindingAlgorithms.DFS
import pathfindingAlgorithms.Dijkstra


abstract class GraphContract<T>(size: Int) {
    // PROPERTIES
    val adjacencyList = adjacencyListInit(size)
    val weightlessAdjacencyList = weightlessAdjacencyListInit(size)
    protected var nodes = MutableList<T?>(size) { null }
    private var distances = DoubleArray(size) { Double.MAX_VALUE }
    var currentVisited = listOf<T>()
        private set
    private var visited = BooleanArray(size)
    fun getVisited(): List<T> =
        visited.mapIndexed { id, visited -> if (visited) id2Node(id)!! else null }.filterNotNull()

    private var parents = IntArray(size) { -1 } // -1 means no parent

    var depth = 0
        private set
    private lateinit var bfsRunner: BFS
    private lateinit var dfsRunner: DFS
    private lateinit var dikjstraRunner: Dijkstra

    // FUNCTIONS TO OVERRIDE

    abstract fun nodes(): List<T>
    protected abstract fun id2Node(id: Int): T?
    protected abstract fun node2Id(node: T): Int?
    abstract fun addNode(node: T)
    abstract fun addEdge(node1: T, node2: T, weight: Double)
    abstract fun addWeightlessEdge(node1: T, node2: T)

    // FUNCTIONS TO INHERIT
    fun connect(node1: T, node2: T, weight: Double) {
        addEdge(node1, node2, weight)
        addEdge(node2, node1, weight)
    }

    fun connect(node1: T, node2: T, weight: Int) = connect(node1, node2, weight.toDouble())

    fun connectWeightless(node1: T, node2: T) {
        addWeightlessEdge(node1, node2)
        addWeightlessEdge(node2, node1)
    }

    fun adjacencyListInit(size: Int): AdjacencyList = MutableList(size) { mutableListOf() }
    fun weightlessAdjacencyListInit(size: Int): WeightlessAdjacencyList = MutableList(size) { mutableListOf() }


    fun distanceTo(node: T): Double {
        val id = node2Id(node) ?: error("Node $node not found in graph")
        return distances[id]
    }

    fun maxDistance() = distances.maxOrNull() ?: Double.MAX_VALUE
    fun furthestNode() = id2Node(distances.let { d -> d.indices.maxBy { d[it] } })!!

    fun bfs(startNodes: List<T>, target: T? = null) {
        val nodeIds = startNodes.map { node -> node2Id(node) ?: error("Node $node not found in graph") }
        val targetId = target?.let { node2Id(it) } ?: -1
        bfsRunner = BFS(weightlessAdjacencyList)
        bfsRunner.bfs(nodeIds, targetId)
        distances = bfsRunner.distances
        parents = bfsRunner.parents
        currentVisited = bfsRunner.getCurrentVisitedIds().mapNotNull { id2Node(it) }
    }

    private fun useWeightedConnectionsIfNeededFor(algorithmName: String) {
        // TODO: make weightlessAdjacencyList from normal one if needed, and warn if weight information is lost.
        val weightlessConnections = nrOfConnections(weightlessAdjacencyList)
        val weightedConnections = nrOfConnections(adjacencyList)
        if(weightlessConnections == 0 && weightedConnections > 0){
            System.err.println("Warning, $algorithmName doesen't work with weighted graphs, ")
        }
    }

    private fun <T> nrOfConnections(twoDList: List<List<T>>) = twoDList.sumOf { it.size }

    fun bfs(startNode: T, target: T? = null) = bfs(listOf(startNode), target)

    fun dfs(startNode: T, reset: Boolean = true) {
        val startId = node2Id(startNode) ?: error("Node $startNode not found in graph")
        dfsRunner = if (reset) DFS(weightlessAdjacencyList) else DFS(weightlessAdjacencyList, visited)
        dfsRunner.dfs(startId)
        currentVisited = dfsRunner.getAndClearCurrentVisited().mapNotNull { id2Node(it) }
        visited = dfsRunner.visited
        depth = dfsRunner.depth
    }

    fun dijkstra(startNode: T, target: T? = null) {
        require(adjacencyList.sumOf { it.size } >= 0) {
            "adjacencyList is empty. Cannot perform Dijkstra."
        }
        val startId = node2Id(startNode) ?: error("Node $startNode not found in graph")
        val targetId = target?.let { node2Id(it) } ?: -1
        dikjstraRunner = Dijkstra(adjacencyList)
        dikjstraRunner.dijkstra(startId, targetId)
        distances = dikjstraRunner.distances
        parents = dikjstraRunner.parents
    }

    fun topologicalSort() = DFS(weightlessAdjacencyList).topologicalSort()
    fun stronglyConnectedComponents() = DFS(weightlessAdjacencyList).stronglyConnectedComponents()
    fun getPath(target: T): List<T> {
        val targetId = node2Id(target)
        val pathIds = getPath(targetId, parents)
        return pathIds.mapNotNull { id2Node(it) }
    }

    private fun getPath(destination: Int?, parents: IntArray): List<Int> {
        val path = mutableListOf<Int>()
        destination?.let { dest ->
            var current = dest
            while (parents[current] != -1) {
                path.add(current)
                current = parents[current]
            }
            path.add(current)
        }
        return path.reversed()
    }

    fun addEdge(node1: T, node2: T, weight: Int) {
        addEdge(node1, node2, weight.toDouble())
    }

    fun printConnections() = printAdjacencyList(false)
    fun printWeightlessConnections() = printAdjacencyList(true)

    private fun printAdjacencyList(weightless: Boolean) =
        (if (weightless) weightlessAdjacencyList else adjacencyList).forEachIndexed { node, connections ->
            System.err.println("$node ---> $connections")
        }
}
