package pathfindingAlgorithms

import UnweightedAdjacencyList


class DFS(private val graph: UnweightedAdjacencyList) {
    private var processedOrder = mutableListOf<Int>()
    private var r = GraphSearchResults(graph.size)

    fun dfs(
        start: Int,
        initialSearchResults: GraphSearchResults? = null,
    ): GraphSearchResults {
        r = initialSearchResults ?: GraphSearchResults(graph.size)
        processedOrder.clear()
        var currentDepth = 0
        DeepRecursiveFunction<Int, Unit> { id ->
            if (r.visited[id]) return@DeepRecursiveFunction
            r.visited[id] = true
            r.currentVisited.add(id)
            r.depth = (++currentDepth).coerceAtLeast(r.depth)
            graph[id].forEach { v ->
                r.parents[v] = id
                this.callRecursive(v)
            }
            processedOrder.add(id)
            currentDepth-- //Done with this node. Backtracking to previous one.
        }.invoke(start)
        return r
    }

    fun stronglyConnectedComponents(): List<List<Int>> {
        val reversedGraph: UnweightedAdjacencyList =
            MutableList<MutableList<Int>>(graph.size) { mutableListOf() }.apply {
                graph.forEachIndexed { u, neighbors ->
                    neighbors.forEach { v ->
                        this[v].add(u)
                    }
                }
            }
        val topologicalOrder = DFS(reversedGraph).topologicalSort().reversed()
        val stronglyConnectedComponents = mutableListOf<List<Int>>()
        topologicalOrder.forEach { id ->
            if (r.visited[id])
                return@forEach
            val searchResults = dfs(id)
            stronglyConnectedComponents.add(searchResults.currentVisited)
        }
        return stronglyConnectedComponents
    }

    fun topologicalSort(): List<Int> {
        for (i in 0 until graph.size) {
            dfs(i)
        }
        return processedOrder//.reversed() //Reversed depending on the order
    }

    /* TODO: Delete
    fun getAndClearCurrentVisited() =
        currentVisited.map { it }.also { currentVisited.clear() } // Deep copy not clear return

    fun clearCurrentVisitedIds() {
        currentVisited.clear()
    }*/
}
