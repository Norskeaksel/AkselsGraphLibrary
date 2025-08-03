package graphClasses

import pathfindingAlgorithms.BFS
import pathfindingAlgorithms.DFS
import pathfindingAlgorithms.Dijkstra
import pathfindingAlgorithms.getPath

abstract class GraphContract<T>(size: Int) {
    // PROPERTIES
    val adjacencyList = adjacencyListInit(size)
    val weightlessAdjacencyList = weightlessAdjacencyListInit(size)
    protected var nodes = MutableList<T?>(size){ null}
    var distances = IntArray(size) { Int.MAX_VALUE }
        private set
    var depth = 0
        private set
    protected open var parents = IntArray(size) { -1 }
    protected open lateinit var bfs: BFS
    open lateinit var dfs: DFS
    open var dikjstraRunner: Dijkstra? = null
    
    // FUNCTIONS TO OVERRIDE
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


    fun distanceTo(node: T): Int {
        val id = node2Id(node) ?: error("Node $node not found in graph")
        return distances[id]
    }
    fun bfs(startNodes: List<T>, target: T? = null) {
        val nodeIds = startNodes.map { node -> node2Id(node) ?: error("Node $node not found in graph") }
        val targetId = target?.let { node2Id(it) } ?: -1
        bfs = BFS(weightlessAdjacencyList)
        bfs.bfs(nodeIds, targetId)
        distances = bfs.distances
        parents = bfs.parents
    }

    fun bfs(startNode: T, target: T? = null) = bfs(listOf(startNode), target)

    fun dfs(startNode: T) {
        val startId = node2Id(startNode) ?: error("Node $startNode not found in graph")
        dfs = DFS(weightlessAdjacencyList)
        dfs.dfs(startId)
        depth = dfs.depth
    }

    fun getPath(target: T): List<T> {
        val targetId = node2Id(target)
        val pathIds = getPath(targetId, parents)
        return pathIds.mapNotNull { id2Node(it) }
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
