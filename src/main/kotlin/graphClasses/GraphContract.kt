package graphClasses

import pathfindingAlgorithms.BFS
import pathfindingAlgorithms.DFS
import pathfindingAlgorithms.Dijkstra


abstract class GraphContract<T>(size: Int) {
    // PROPERTIES
    val adjacencyList = adjacencyListInit(size)
    val weightlessAdjacencyList = weightlessAdjacencyListInit(size)
    protected var nodes = MutableList<T?>(size) { null }
    var distances = DoubleArray(size) { Double.MAX_VALUE }
        private set
    private var currentVisited = listOf<T>()
    private var visited = BooleanArray(size)
    fun getVisited(): List<T> =
        visited.mapIndexed { id, visited -> if (visited) id2Node(id)!! else null }.filterNotNull()

    lateinit var parents: List<T>
        private set

    var depth = 0
        private set
    protected open lateinit var bfsRunner: BFS
    protected open lateinit var dfsRunner: DFS
    protected open lateinit var dikjstraRunner: Dijkstra

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


    fun distanceTo(node: T): Double {
        val id = node2Id(node) ?: error("Node $node not found in graph")
        return distances[id]
    }

    fun bfs(startNodes: List<T>, target: T? = null) {
        require(weightlessAdjacencyList.sumOf { it.size } >= 0) {
            "weightlessAdjacencyList is empty. Cannon perform BFS."
        }
        val nodeIds = startNodes.map { node -> node2Id(node) ?: error("Node $node not found in graph") }
        val targetId = target?.let { node2Id(it) } ?: -1
        bfsRunner = BFS(weightlessAdjacencyList)
        bfsRunner.bfs(nodeIds, targetId)
        distances = bfsRunner.distances.map { it.toDouble() }.toDoubleArray()
        parents = bfsRunner.parents.map { id2Node(it) }.filterNotNull()
        currentVisited = bfsRunner.getCurrentVisitedIds().mapNotNull { id2Node(it) }
    }

    fun bfs(startNode: T, target: T? = null) = bfs(listOf(startNode), target)

    fun dfs(startNode: T, reset:Boolean = true) {
        require(weightlessAdjacencyList.sumOf { it.size } >= 0) {
            "weightlessAdjacencyList is empty. Cannot perform DFS."
        }
        val startId = node2Id(startNode) ?: error("Node $startNode not found in graph")
        dfsRunner = if(reset) DFS(weightlessAdjacencyList) else DFS(weightlessAdjacencyList, visited)
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
        parents = dikjstraRunner.parents.map { id2Node(it)!! }
    }

    fun topologicalSort() = DFS(weightlessAdjacencyList).topologicalSort()
    fun stronglyConnectedComponents() = DFS(weightlessAdjacencyList).stronglyConnectedComponents()
    fun getAndClearCurrentVisitedIds() =
        currentVisited.map { it }.also { currentVisited = listOf() } // Deep copy not clear return

    fun getPath(target: T): List<T> {
        val targetId = node2Id(target)
        val pathIds = getPath(targetId, parents.map { node2Id(it)!! }.toIntArray())
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
