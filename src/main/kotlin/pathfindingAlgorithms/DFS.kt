package pathfindingAlgorithms

import UnweightedAdjacencyList


class DFS(
    private val graph: UnweightedAdjacencyList,
    val visited: BooleanArray = BooleanArray(graph.size),
    val deleted: BooleanArray = BooleanArray(graph.size),
) {
    private var processedOrder = mutableListOf<Int>()
    private var r = GraphSearchResults(graph.size)

    fun dfs(start: Int): GraphSearchResults {
        if (deleted[start]) error("Starting node is deleted, cannot perform DFS.")
        var currentDepth = 0
        clearCurrentVisitedIds()
        DeepRecursiveFunction<Int, Unit> { id ->
            if (r.visited[id] || deleted[id]) return@DeepRecursiveFunction
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
        val topologicalOrder = DFS(reversedGraph, deleted).topologicalSort().reversed()
        val stronglyConnectedComponents = mutableListOf<List<Int>>()
        topologicalOrder.forEach { id ->
            if (visited[id])
                return@forEach
            dfs(id)
            stronglyConnectedComponents.add(getAndClearCurrentVisited())
        }
        return stronglyConnectedComponents
    }

    fun topologicalSort(): List<Int> {
        for (i in 0 until graph.size) {
            if (deleted[i]) continue
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
