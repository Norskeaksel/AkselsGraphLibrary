package pathfindingAlgorithms

import UnweightedAdjacencyList


class DFS(
    private val graph: UnweightedAdjacencyList,
    val visited: BooleanArray = BooleanArray(graph.size),
    val nodesAreDeleted: List<Boolean> = MutableList(graph.size) { false }
) {
    var size = 0

    init {
        size = graph.size
    }

    var processedOrder = mutableListOf<Int>()
    var depth = 0

    private var currentVisitedDepts = mutableListOf<Int>()
    private var currentVisited = mutableListOf<Int>()
    val parent = IntArray(size) { -1 }

    fun dfsSimple(currentId: Int) {
        if (currentId in currentVisited) // Slow but simple. Preserves order in visited Ids
            return
        currentVisited.add(currentId)
        graph[currentId].forEach { connectedNodeId ->
            dfsSimple(connectedNodeId)
        }
    }

    fun dfs(start: Int) {
        if(nodesAreDeleted?.get(start) == true){
            System.err.println("Warning: Starting node is deleted, cannot perform DFS.")
            return
        }
        var currentDepth = 0
        clearCurrentVisitedIds()
        DeepRecursiveFunction<Int, Unit> { id ->
            if (visited[id] || (nodesAreDeleted?.get(id) == true)) return@DeepRecursiveFunction
            visited[id] = true
            currentVisited.add(id)
            currentVisitedDepts.add(currentDepth)
            currentDepth++
            depth = currentDepth.coerceAtLeast(depth)

            graph[id].forEach { v ->
                parent[v] = id
                this.callRecursive(v)
            }
            processedOrder.add(id)
            //Done with this node. Backtracking to previous one.
            currentDepth--
        }.invoke(start)
    }

    fun stronglyConnectedComponents(): List<List<Int>> {
        val reversedGraph: UnweightedAdjacencyList = MutableList<MutableList<Int>>(size) { mutableListOf() }.apply {
            graph.forEachIndexed { u, neighbors ->
                neighbors.forEach { v ->
                    this[v].add(u)
                }
            }
        }
        val topologicalOrder = DFS(reversedGraph, nodesAreDeleted = nodesAreDeleted).topologicalSort().reversed()
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
        for (i in 0 until size) {
            if (nodesAreDeleted?.get(i) == true) continue
            dfs(i)
        }
        return processedOrder//.reversed() //Reversed depending on the order
    }

    fun getAndClearCurrentVisited() =
        currentVisited.map { it }.also { currentVisited.clear() } // Deep copy not clear return

    fun clearCurrentVisitedIds() {
        currentVisited.clear()
    }
}
