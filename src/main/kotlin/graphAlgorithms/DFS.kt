package graphAlgorithms

import UnweightedAdjacencyList


class DFS(private val graph: UnweightedAdjacencyList) {
    private var r = GraphSearchResults(graph.size)

    fun dfs(
        start: Int,
        initialSearchResults: GraphSearchResults? = null,
    ): GraphSearchResults {
        r = initialSearchResults ?: GraphSearchResults(graph.size)
        r.currentVisited = mutableListOf()
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
            r.processedOrder.add(id)
            currentDepth-- //Done with this node. Backtracking to previous one.
        }.invoke(start)
        return r
    }

    fun stronglyConnectedComponents(deleted:BooleanArray = BooleanArray(graph.size)): List<List<Int>> {
        val reversedGraph: UnweightedAdjacencyList =
            MutableList<MutableList<Int>>(graph.size) { mutableListOf() }.apply {
                graph.forEachIndexed { u, neighbors ->
                    neighbors.forEach { v ->
                        this[v].add(u)
                    }
                }
            }
        val topologicalOrder = DFS(reversedGraph).topologicalSort(deleted).reversed()
        val stronglyConnectedComponents = mutableListOf<List<Int>>()
        topologicalOrder.forEach { id ->
            if (r.visited[id])
                return@forEach
            dfs(id, r)
            stronglyConnectedComponents.add(r.currentVisited)
        }
        return stronglyConnectedComponents
    }

    fun topologicalSort(deleted:BooleanArray = BooleanArray(graph.size)): List<Int> {
        for (i in 0 until graph.size) {
            if(deleted[i]) continue
            dfs(i, r)
        }
        return r.processedOrder//.reversed() //Reversed depending on the order
    }
}
