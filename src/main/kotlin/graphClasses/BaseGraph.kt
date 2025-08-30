package graphClasses

import AdjacencyList
import Edge
import UnweightedAdjacencyList
import pathfindingAlgorithms.*
import toUnweightedAdjacencyList


abstract class BaseGraph<T>(size: Int) {
    // PROPERTIES AND INITIALIZATION
    protected val adjacencyList: AdjacencyList = MutableList(size) { mutableListOf() }
    protected var unweightedAdjacencyList: UnweightedAdjacencyList = MutableList(size) { mutableListOf() }
        get() {
            if (nrOfConnections(field) < nrOfConnections(adjacencyList)) {
                field = adjacencyList.toUnweightedAdjacencyList()
            }
            return field
        }
    protected var _nodes: MutableList<T?> = MutableList(size) { null }
    val nodes: List<T>
        get() = _nodes.filterNotNull()
    protected var searchResults: GraphSearchResults? = null

    // ABSTRACT FUNCTIONS
    abstract fun getAllNodes(): List<T>
    protected abstract fun id2Node(id: Int): T?
    protected abstract fun node2Id(node: T): Int?
    abstract fun addNode(node: T)
    abstract fun addEdge(node1: T, node2: T, weight: Double)
    abstract fun addUnweightedEdge(node1: T, node2: T)

    // CORE GRAPH OPERATIONS
    fun size() = nodes.size
    fun connect(node1: T, node2: T, weight: Number) {
        val weightDouble = weight.toDouble()
        addEdge(node1, node2, weightDouble)
        addEdge(node2, node1, weightDouble)
    }

    fun connect(node1: T, node2: T) = connect(node1, node2, 1.0)
    fun connectUnweighted(node1: T, node2: T) {
        addUnweightedEdge(node1, node2)
        addUnweightedEdge(node2, node1)
    }

    fun removeEdge(node1: T, node2: T, weight: Double? = null) {
        val u = node2Id(node1) ?: error("Node $node1 not found in graph")
        val v = node2Id(node2) ?: error("Node $node2 not found in graph")
        removeEdge(u, v, weight)
    }

    private fun removeEdge(id1: Int, id2: Int, weight: Double? = null) {
        if (weight == null)
            adjacencyList[id1].removeAll { it.second == id2 }
        else
            adjacencyList[id1].remove(Edge(weight, id2))
        if (nrOfConnections(unweightedAdjacencyList) > nrOfConnections(adjacencyList)) {
            unweightedAdjacencyList[id1].remove(id2)
        }
    }

    // GRAPH INFORMATION
    fun depth() = searchResults?.depth ?: error("Can't retrieve depth because no search has been run yet")
    fun visitedNodes() = searchResults?.run { visited.indices.mapNotNull { if (visited[it]) id2Node(it) else null } }
        ?: error("Can't retrieve visitedNodes because no search has been run yet")

    fun currentVisitedNodes(): List<T> =
        searchResults?.currentVisited?.map { id2Node(it)!! }
            ?: error("Can't retrieve currentVisitedNodes because no search has been run yet")

    fun foundTarget() = searchResults?.foundTarget ?: false
    fun distanceTo(node: T): Double {
        val id = node2Id(node) ?: error("Node $node not found in graph")
        searchResults?.let {
            return it.distances[id]
        }
        error("Haven't computed distance to $node because no search algorithm (dfs, bfs, dijkstra) has been run yet.")
    }

    fun maxDistance() = searchResults?.distances?.maxOrNull() ?: Double.MAX_VALUE
    fun furthestNode(): T =
        searchResults?.let { r -> id2Node(r.distances.indices.first { r.distances[it] == maxDistance() })!! }
            ?: error("Haven't computed furthest node because no search algorithm (dfs, bfs, dijkstra) has been run yet.")

    fun getNeighbours(t: T): List<T> =
        node2Id(t)?.let { unweightedAdjacencyList[it] }?.map { id2Node(it)!! }
            ?: error("Node $t not found in graph")

    // SEARCH ALGORITHMS
    fun bfs(startNodes: List<T>, target: T? = null, reset: Boolean = true) {
        useWeightedConnectionsIfNeeded()
        val startNodeIds = startNodes.map { node -> node2Id(node) ?: error("Node $node not found in graph") }
        val targetId = target?.let { node2Id(it) } ?: -1
        if (reset) searchResults = null
        searchResults = BFS(unweightedAdjacencyList).bfs(startNodeIds, targetId, searchResults)
    }

    fun bfs(startNode: T, target: T? = null) = bfs(listOf(startNode), target)
    fun dfs(startNode: T, reset: Boolean = true) {
        val startId = node2Id(startNode) ?: error("Node $startNode not found in graph")
        if (reset) searchResults = null
        searchResults = DFS(unweightedAdjacencyList).dfs(startId, searchResults)
    }

    fun dijkstra(startNode: T, target: T? = null) {
        if (nrOfConnections(adjacencyList) == 0) {
            System.err.println("Warning: The adjacently list has no connections, making pathfinding infeasible.")
            if (nrOfConnections(unweightedAdjacencyList) != 0) {
                System.err.println("The graph does have unweighted connections. Executing BFS instead.")
                bfs(startNode, target)
                return
            }
        }
        val startId = node2Id(startNode) ?: error("Node $startNode not found in graph")
        val targetId = target?.let { node2Id(it) } ?: -1
        searchResults = Dijkstra(adjacencyList).dijkstra(startId, targetId, searchResults)
    }

    // ADDITIONAL ALGORITHMS
    fun minimumSpanningTree(): Pair<Double, Graph> = minimumSpanningTree(adjacencyList).run {
        first to second.let { adjacencyList ->
            val mstGraph = Graph()
            adjacencyList.forEachIndexed { id, edges ->
                edges.forEach { (w, v) ->
                    mstGraph.connect(id2Node(id)!!, id2Node(v)!!, w)
                }
            }
            mstGraph
        }
    }

    open fun topologicalSort() = DFS(unweightedAdjacencyList).topologicalSort()
    open fun stronglyConnectedComponents() = DFS(unweightedAdjacencyList).stronglyConnectedComponents()

    // PATH UTILITIES
    fun getPath(target: T): List<T> {
        val targetId = node2Id(target)
        val pathIds = searchResults?.let { getPath(targetId, it.parents) }
            ?: error("Can't getPath because no search has been run yet")
        return pathIds.mapNotNull { id2Node(it) }
    }

    private fun getPath(destination: Int?, parents: IntArray): List<Int> {
        val path = mutableListOf<Int>()
        destination?.let { dest ->
            var current = dest
            while (parents[current] != -1 && parents[current] != destination) {
                path.add(current)
                current = parents[current]
            }
            path.add(current)
        }
        return path.reversed()
    }

    // HELPER FUNCTIONS
    fun resetSearchResults() {
        searchResults = GraphSearchResults(_nodes.size)
    }

    private fun useWeightedConnectionsIfNeeded() {
        if (nrOfConnections(unweightedAdjacencyList) == 0) {
            unweightedAdjacencyList = adjacencyList.toUnweightedAdjacencyList()
            if (nrOfConnections(unweightedAdjacencyList) == 0) {
                System.err.println("Warning: The graph has no connections, making pathfinding infeasible.")
            } else {
                System.err.println(
                    "Warning: An unweighted adjacency list is required. " +
                            "Building one from the adjacencyList."
                )
            }
        }
    }

    protected fun <T> nrOfConnections(twoDList: List<List<T>>) = twoDList.sumOf { it.size }

    // PRINTER FUNCTIONS
    fun printConnections() = printGraph(false)
    fun printWeightlessConnections() = printGraph(true)
    private fun printGraph(weightless: Boolean) =
        if (weightless) {
            unweightedAdjacencyList.forEachIndexed { nodeId, connections ->
                System.err.println("${id2Node(nodeId)} ---> ${connections.map { id2Node(it) }}")
            }
        } else {
            adjacencyList.forEachIndexed { nodeId, connections ->
                System.err.println("${id2Node(nodeId)} ---> ${connections.map { "(${it.first}, ${id2Node(it.second)})" }}")
            }
        }
}
