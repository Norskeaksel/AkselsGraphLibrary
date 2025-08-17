package graphClasses

import AdjacencyList
import Edge
import WeightlessAdjacencyList
import pathfindingAlgorithms.BFS
import pathfindingAlgorithms.DFS
import pathfindingAlgorithms.Dijkstra
import toWeightlessAdjacencyList


abstract class BaseGraph<T>(size: Int) {
    // PROPERTIES
    val adjacencyList = adjacencyListInit(size)

    /** A cached, weightless representation of the graph's adjacency list, that is updated whenever it's needed. */
    var unweightedAdjacencyList: WeightlessAdjacencyList = MutableList(size) { mutableListOf() }
        get() {
            if (nrOfConnections(field) < nrOfConnections(adjacencyList)) {
                System.err.println("Optimizing adjacency list by converting to weightless representation.")
                field = adjacencyList.toWeightlessAdjacencyList()
            }
            return field
        }

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

    abstract fun getAllNodes(): List<T>
    protected abstract fun id2Node(id: Int): T?
    protected abstract fun node2Id(node: T): Int?
    abstract fun addNode(node: T)
    abstract fun addEdge(node1: T, node2: T, weight: Double = 1.0)

    // FUNCTIONS TO INHERIT
    fun <N : Number> connect(node1: T, node2: T, weight: N) {
        val weightDouble = weight.toDouble()
        addEdge(node1, node2, weightDouble)
        addEdge(node2, node1, weightDouble)
    }

    fun connect(node1: T, node2: T) = connect(node1, node2, 1.0)
    private fun removeEdge(id1: Int, id2: Int, weight: Double? = null) {
        if (weight == null)
            adjacencyList[id1].removeAll { it.second == id2 }
        else
            adjacencyList[id1].remove(Edge(weight, id2))
        if (nrOfConnections(unweightedAdjacencyList) > nrOfConnections(adjacencyList)) {
            unweightedAdjacencyList[id1].remove(id2)
        }
    }

    fun removeEdge(node1: T, node2: T, weight: Double? = null) {
        val u = node2Id(node1) ?: run {
            System.err.println("Node $node1 not found in graph")
            return
        }
        val v = node2Id(node2) ?: run {
            System.err.println("Node $node2 not found in graph")
            return
        }
        removeEdge(u, v, weight)
    }

    fun adjacencyListInit(size: Int): AdjacencyList = MutableList(size) { mutableListOf() }

    fun distanceTo(node: T): Double {
        val id = node2Id(node) ?: error("Node $node not found in graph")
        return distances[id]
    }

    fun maxDistance() = distances.maxOrNull() ?: Double.MAX_VALUE
    fun furthestNode() = id2Node(distances.let { d -> d.indices.maxBy { d[it] } })!!

    fun bfs(startNodes: List<T>, target: T? = null) {
        useWeightedConnectionsIfNeeded()
        val nodeIds = startNodes.map { node -> node2Id(node) ?: error("Node $node not found in graph") }
        val targetId = target?.let { node2Id(it) } ?: -1
        bfsRunner = BFS(unweightedAdjacencyList)
        bfsRunner.bfs(nodeIds, targetId)
        distances = bfsRunner.distances
        parents = bfsRunner.parents
        currentVisited = bfsRunner.getCurrentVisitedIds().mapNotNull { id2Node(it) }
    }

    private fun useWeightedConnectionsIfNeeded() {
        if (nrOfConnections(unweightedAdjacencyList) == 0) {
            unweightedAdjacencyList = adjacencyList.toWeightlessAdjacencyList()
            if (nrOfConnections(unweightedAdjacencyList) == 0) {
                System.err.println("Warning, the graph has no connections, making pathfinding infeasible.")
            }
        }
    }

    private fun <T> nrOfConnections(twoDList: List<List<T>>) = twoDList.sumOf { it.size }

    fun bfs(startNode: T, target: T? = null) = bfs(listOf(startNode), target)

    fun dfs(startNode: T, reset: Boolean = true) {
        val startId = node2Id(startNode) ?: error("Node $startNode not found in graph")
        dfsRunner = if (reset) DFS(unweightedAdjacencyList) else DFS(unweightedAdjacencyList, visited)
        dfsRunner.dfs(startId)
        currentVisited = dfsRunner.getAndClearCurrentVisited().mapNotNull { id2Node(it) }
        visited = dfsRunner.visited
        depth = dfsRunner.depth
    }

    fun dijkstra(startNode: T, target: T? = null) {
        if (nrOfConnections(adjacencyList) < nrOfConnections(unweightedAdjacencyList)) {
            System.err.println("Warning: adjacencyList has fewer connections than the unweightedAdjacencyList, indicating an error in the graph construction.")
        }
        val startId = node2Id(startNode) ?: error("Node $startNode not found in graph")
        val targetId = target?.let { node2Id(it) } ?: -1
        dikjstraRunner = Dijkstra(adjacencyList)
        dikjstraRunner.dijkstra(startId, targetId)
        distances = dikjstraRunner.distances
        parents = dikjstraRunner.parents
    }

    fun topologicalSort() = DFS(unweightedAdjacencyList).topologicalSort()
    fun stronglyConnectedComponents() = DFS(unweightedAdjacencyList).stronglyConnectedComponents()
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
        (if (weightless) unweightedAdjacencyList else adjacencyList).forEachIndexed { node, connections ->
            System.err.println("$node ---> $connections")
        }
}
