package graphClasses

import pathfindingAlgorithms.BFS
import pathfindingAlgorithms.DFS
import pathfindingAlgorithms.Dijkstra
import pathfindingAlgorithms.getPath

abstract class GraphContract<T>(size: Int) {
    // PROPERTIES
    protected val adjacencyList = adjacencyListInit(size)
    protected val weightlessAdjacencyList = weightlessAdjacencyListInit(size)
    protected open  var nodes = MutableList<T?>(size){ null}
    protected open var distances = IntArray(size) { Int.MAX_VALUE }
    protected open var parents = IntArray(size) { -1 }
    protected open lateinit var bfs: BFS
    open var dfsRunner: DFS? = null
    open var dikjstraRunner: Dijkstra? = null
    
    // FUNCTIONS TO OVERRIDE
    abstract fun addNode(node: T)
    abstract fun addEdge(node1: T, node2: T, weight: Double = 1.0)
    abstract fun addWeightlessEdge(node1: T, node2: T)
    abstract fun getAdjacencyList(): AdjacencyList
    abstract fun id2Node(id: Int): T?
    abstract fun node2Id(node: T): Int?
    open fun getWeightlessAdjacencyList() = getAdjacencyList().toWeightlessAdjacencyList()

    // FUNCTIONS TO INHERIT

    fun distanceTo(node: T): Int {
        val id = node2Id(node) ?: error("Node $node not found in graph")
        return distances[id]
    }
    fun bfs(nodes: List<T>){
        val nodeId = nodes.map {node -> node2Id(node) ?: error("Node $node not found in graph") }
        bfs = BFS(getWeightlessAdjacencyList())
        bfs.bfs(nodeId)
    }
    
    fun bfs(node: T) = bfs(listOf(node))

    fun getPath(target: T): List<T> {
        val targetId = node2Id(target)
        val pathIds = getPath(targetId, parents)
        return pathIds.mapNotNull { id2Node(it) }
    }
    fun addEdge(node1: T, node2: T, weight: Int) {
        addEdge(node1, node2, weight.toDouble())
    }

    fun connect(node1: T, node2: T, weight: Double = 1.0) {
        addEdge(node1, node2, weight)
        addEdge(node2, node1, weight)
    }

    fun connect(node1: T, node2: T, weight: Int) = connect(node1, node2, weight.toDouble())

    fun adjacencyListInit(size: Int): AdjacencyList = MutableList(size) { mutableListOf() }
    fun weightlessAdjacencyListInit(size: Int): WeightlessAdjacencyList = MutableList(size) { mutableListOf() }
    fun printConnections() = printAdjacencyList(false)
    fun printWeightlessConnections() = printAdjacencyList(true)

    private fun printAdjacencyList(weightless: Boolean) =
        (if (weightless) getWeightlessAdjacencyList() else getAdjacencyList()).forEachIndexed { node, connections ->
            System.err.println("$node ---> $connections")
        }
}
