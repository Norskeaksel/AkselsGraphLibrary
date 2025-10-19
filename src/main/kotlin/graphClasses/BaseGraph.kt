package graphClasses

import AdjacencyList
import UnweightedAdjacencyList
import graphAlgorithms.*
import toUnweightedAdjacencyList
import toWeightedAdjacencyList


abstract class BaseGraph<T : Any>(size: Int, val isWeighted: Boolean = true) {
    // PROPERTIES AND INITIALIZATION
    protected val adjacencyList: AdjacencyList = MutableList(size) { mutableListOf() }
    protected var unweightedAdjacencyList: UnweightedAdjacencyList = MutableList(size) { mutableListOf() }

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
    /** Adds an edge between two nodes in the graph, and creates the nodes if they don't exist.
     *
     * If the graph is unweighted, the edge is added without a weight. If a weight is provided
     * for an unweighted graph, an error is thrown. If the graph is weighted, a weight must be provided;
     * otherwise, an error is thrown.
     *
     * @param node1 The starting node of the edge.
     * @param node2 The ending node of the edge.
     * @param weight The weight of the edge (required for weighted graphs). Defaults to `null`.
     * @throws IllegalStateException If a weight is provided for an unweighted graph or if no weight
     * is provided for a weighted graph. */
    fun addEdge(node1: T, node2: T, weight: Number? = null) {
        if (!isWeighted) {
            if (weight != null)
                error(
                    "A weight cannot be given to a unweighted graph. Don't provide a weight or make the graph " +
                            "unweighted by setting the parameter isWeighted=true when creating it."
                )
            addUnweightedEdge(node1, node2)
        } else if (weight == null) error(
            "A weight must be provided when adding edges in a weighted graph. " +
                    "To make the graph unweighted, set the parameter isWeighted=false when creating it."
        )
        else addWeightedEdge(node1, node2, weight.toDouble())
    }

    /** @return The total number of nodes in the graph. */
    fun size() = nodes().size

    /** Connects two nodes in the graph, by calling addEdge(node1,node2, weight) and addEdge(node2, node1, weight)
     *
     * @param node1 The first node to connect.
     * @param node2 The second node to connect.
     * @param weight The weight of the connection (required for weighted graphs). Defaults to `null`.
     * @throws IllegalStateException If no weight is provided for a weighted graph. */
    fun connect(node1: T, node2: T, weight: Number? = null) {
        addEdge(node1, node2, weight)
        addEdge(node2, node1, weight)
    }

    /** Removes the edge(s) between two nodes in the graph.
     *
     * @param node1 The first node of the edge to remove.
     * @param node2 The second node of the edge to remove.
     * @throws IllegalStateException If either node is not found in the graph. */
    fun removeEdge(node1: T, node2: T) {
        val u = node2Id(node1) ?: error("Node $node1 not found in graph")
        val v = node2Id(node2) ?: error("Node $node2 not found in graph")
        removeEdge(u, v)
    }

    private fun removeEdge(id1: Int, id2: Int) {
        if (isWeighted) adjacencyList[id1].removeAll { it.second == id2 }
        else unweightedAdjacencyList[id1].remove(id2)
    }

    // GRAPH INFORMATION
    fun depth() = searchResults?.depth ?: error("Can't retrieve depth because no search has been run yet")
    fun visitedNodes() = searchResults?.run { visited.indices.mapNotNull { if (visited[it]) id2Node(it) else null } }
        ?: error("Can't retrieve visitedNodes because no search has been run yet")

    fun currentVisitedNodes(): List<T> =
        searchResults?.currentVisited?.map { id2Node(it)!! }
            ?: emptyList()

    fun finalPath(): List<T> = finalPath

    fun foundTarget() = searchResults?.foundTarget ?: false
    fun distanceTo(node: T) =
        if (isWeighted) distanceWeightedTo(node)
        else distanceUnweightedTo(node).toDouble()

    private fun distanceWeightedTo(node: T): Double {
        val id = node2Id(node) ?: error("Node $node not found in graph")
        searchResults?.let {
            return it.distances[id]
        }
        error("Haven't computed distance to $node because no search algorithm (dfs, bfs, dijkstra) has been run yet.")
    }

    private fun distanceUnweightedTo(node: T): Int {
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


    fun edges(t: T): List<Pair<Double, T>> = if (isWeighted) weightedEdges(t)
    else neighbours(t).map { 1.0 to it }

    private fun weightedEdges(t: T): List<Pair<Double, T>> =
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
        (if (isWeighted) adjacencyList else unweightedAdjacencyList.toWeightedAdjacencyList()).let { graph ->
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
