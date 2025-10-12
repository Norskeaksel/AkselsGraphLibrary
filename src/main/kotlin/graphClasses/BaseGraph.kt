package graphClasses

import AdjacencyList
import Edge
import UnweightedAdjacencyList
import graphAlgorithms.*
import toUnweightedAdjacencyList
import toWeightedAdjacencyList


abstract class BaseGraph<T : Any>(size: Int, val isWeighted: Boolean = true) {
    // PROPERTIES AND INITIALIZATION
    protected val adjacencyList: AdjacencyList = MutableList(size) { mutableListOf() }
    protected var unweightedAdjacencyList: UnweightedAdjacencyList = MutableList(size) { mutableListOf() }
    private val connections = mutableListOf<Pair<T, T>>()

    protected var nodes: MutableList<T?> = MutableList(size) { null }
    private var searchResults: GraphSearchResults? = null
    private var finalPath: List<T> = emptyList()
    private var allDistances: Array<DoubleArray>? = null

    // ABSTRACT FUNCTIONS
    abstract fun addNode(node: T)
    protected abstract fun addWeightedEdge(node1: T, node2: T, weight: Double)
    protected abstract fun addUnweightedEdge(node1: T, node2: T)
    protected abstract fun node2Id(node: T): Int?
    protected abstract fun id2Node(id: Int): T?
    abstract fun nodes(): List<T>

    // CORE GRAPH OPERATIONS

    fun addEdge(node1: T, node2: T, weight: Number? = null) {
        if (!isWeighted) addUnweightedEdge(node1, node2)
        else if (weight == null) error(
            "A weight must be provided when adding edges in a weighted graph. " +
                    "To make the graph unweighted, set the parameter isWeighted=false when creating it."
        )
        else addWeightedEdge(node1, node2, weight.toDouble())
    }

    fun size() = nodes().size
    fun connect(node1: T, node2: T, weight: Number? = null) {
        if (!isWeighted) connectUnweighted(node1, node2)
        else if (weight == null) error(
            "A weight must be provided when connecting nodes in a weighted graph. " +
                    "To make the graph unweighted, set the parameter isWeighted=false when creating it."
        )
        else connectWeighted(node1, node2, weight.toDouble())
    }

    private fun connectWeighted(node1: T, node2: T, weight: Double) {
        addWeightedEdge(node1, node2, weight)
        addWeightedEdge(node2, node1, weight)
        connections.add(node1 to node2)
    }

    private fun connectUnweighted(node1: T, node2: T) {
        addUnweightedEdge(node1, node2)
        addUnweightedEdge(node2, node1)
        connections.add(node1 to node2)
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
    private fun weightedEdges() = adjacencyList.flatMapIndexed { u, neighbours ->
        neighbours.map { v -> u to v.second }
    }

    private fun unweightedEdges() = unweightedAdjacencyList.flatMapIndexed { u, neighbours ->
        neighbours.map { v -> u to v }
    }

    fun connections() = connections.toList()
    fun depth() = searchResults?.depth ?: error("Can't retrieve depth because no search has been run yet")
    fun visitedNodes() = searchResults?.run { visited.indices.mapNotNull { if (visited[it]) id2Node(it) else null } }
        ?: error("Can't retrieve visitedNodes because no search has been run yet")

    fun currentVisitedNodes(): List<T> =
        searchResults?.currentVisited?.map { id2Node(it)!! }
            ?: emptyList()

    fun finalPath(): List<T> = finalPath

    fun foundTarget() = searchResults?.foundTarget ?: false
    fun distanceWeightedTo(node: T): Double {
        val id = node2Id(node) ?: error("Node $node not found in graph")
        searchResults?.let {
            return it.distances[id]
        }
        error("Haven't computed distance to $node because no search algorithm (dfs, bfs, dijkstra) has been run yet.")
    }

    fun distanceUnweightedTo(node: T): Int {
        val id = node2Id(node) ?: error("Node $node not found in graph")
        searchResults?.let {
            return it.unweightedDistances[id]
        }
        error("Haven't computed distance to $node because no search algorithm (dfs, bfs, dijkstra) has been run yet.")
    }

    fun maxDistance() = searchResults?.distances?.maxOrNull() ?: Double.MAX_VALUE
    fun furthestNode(): T =
        searchResults?.let { r -> id2Node(r.distances.indices.first { r.distances[it] == maxDistance() })!! }
            ?: error("Haven't computed furthest node because no search algorithm (dfs, bfs, dijkstra) has been run yet.")


    fun weightedEdges(t: T): List<Pair<Double, T>> =
        node2Id(t)?.let { adjacencyList[it] }?.map { Pair(it.first, id2Node(it.second)!!) }
            ?: error("Node $t not found in graph")

    fun neighbours(t: T): List<T> =
        if (isWeighted) weightedEdges(t).map { it.second }
        else node2Id(t)?.let { unweightedAdjacencyList[it] }
            ?.map { id2Node(it)!! }
            ?: error("Node $t not found in graph")

    // SEARCH ALGORITHMS
    fun bfs(startNodes: List<T>, target: T? = null, reset: Boolean = true) {
        useWeightedConnectionsIfNeeded("bfs")
        val startNodeIds = startNodes.map { node -> node2Id(node) ?: error("Node $node not found in graph") }
        val targetId = target?.let { node2Id(it) } ?: -1
        if (reset) searchResults = null
        searchResults = BFS(unweightedAdjacencyList).bfs(startNodeIds, targetId, searchResults)
        target?.let {
            finalPath = getPath(it)
        }
    }

    fun bfs(startNode: T, target: T? = null) = bfs(listOf(startNode), target)
    fun dfs(startNode: T, reset: Boolean = true) {
        val startId = node2Id(startNode) ?: error("Node $startNode not found in graph")
        if (reset) searchResults = null
        useWeightedConnectionsIfNeeded("dfs")
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
        searchResults = Dijkstra(adjacencyList).dijkstra(startId, searchResults)
        target?.let {
            finalPath = getPath(it)
        }
    }

    fun floydWarshall() {
        allDistances = FloydWarshall(adjacencyList).floydWarshall()
    }

    fun distanceFromUtoV(u: T, v: T) = allDistances?.let {
        it[node2Id(u)!!][node2Id(v)!!]
    } ?: error("FloydWarshall must be run before calling distanceFromUtoV")

    // ADDITIONAL ALGORITHMS
    fun minimumSpanningTree(): Pair<Double, Graph> =
        (if (isWeighted) adjacencyList else unweightedAdjacencyList.toWeightedAdjacencyList()).let {graph ->
            return prims(graph).run {
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
        }

    open fun topologicalSort(): List<T> {
        val dfsGraph = if (isWeighted) adjacencyList.toUnweightedAdjacencyList() else unweightedAdjacencyList
        return DFS(dfsGraph).topologicalSort().map { id2Node(it)!! }
    }

    open fun stronglyConnectedComponents(): List<List<T>> {
        useWeightedConnectionsIfNeeded("stronglyConnectedComponents")
        val scc = DFS(unweightedAdjacencyList).stronglyConnectedComponents()
        return scc.map { component -> component.map { id2Node(it)!! } }
    }

    // PATH UTILITIES
    fun getPath(target: T): List<T> {
        val targetId = node2Id(target)
        val pathIds = searchResults?.let { getPath(targetId, it.parents) }
            ?: error("Can't getPath because no search has been run yet")
        val path = pathIds.mapNotNull { id2Node(it) }
        return path
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
        searchResults = GraphSearchResults(nodes.size)
    }

    private fun useWeightedConnectionsIfNeeded(algorithmName: String) {
        if (nrOfConnections(unweightedAdjacencyList) == 0) {
            unweightedAdjacencyList = adjacencyList.toUnweightedAdjacencyList()
            if (nrOfConnections(unweightedAdjacencyList) == 0) {
                System.err.println("Warning: The graph has no connections, making pathfinding infeasible.")
            } else {
                System.err.println(
                    "Warning: An unweighted adjacency list is required for $algorithmName. " +
                            "Building one from the weighted adjacencyList."
                )
            }
        }
    }

    protected fun <T> nrOfConnections(twoDList: List<List<T>>) = twoDList.sumOf { it.size }

    // PRINTER FUNCTIONS
    fun printWeightedConnections() = printGraph(false)
    fun printUnweightedConnections() = printGraph(true)
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
