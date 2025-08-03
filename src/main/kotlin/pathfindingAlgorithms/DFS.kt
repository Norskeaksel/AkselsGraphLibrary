package pathfindingAlgorithms

import graphClasses.WeightlessAdjacencyList


class DFS(private val weightlessAdjacencyList: WeightlessAdjacencyList) {
    var size = 0

    init {
        size = weightlessAdjacencyList.size
    }

    var visited = BooleanArray(size)
    var processedOrder = mutableListOf<Int>()
    var depth = 0

    private var currentVisitedDepts = mutableListOf<Int>()
    private var currentVisitedIds = mutableListOf<Int>()
    val parent = IntArray(size) { -1 }

    fun dfsSimple(currentId: Int) {
        if (currentId in currentVisitedIds) // Slow but simple. Preserves order in visited Ids
            return
        currentVisitedIds.add(currentId)
        weightlessAdjacencyList[currentId].forEach { connectedNodeId ->
            dfsSimple(connectedNodeId)
        }
    }

    fun dfs(start: Int) {
        var currentDepth = 0
        clearCurrentVisitedIds()
        DeepRecursiveFunction<Int, Unit> { id ->
            if (visited[id])
                return@DeepRecursiveFunction
            visited[id] = true
            currentVisitedIds.add(id)
            currentVisitedDepts.add(currentDepth)
            currentDepth++
            depth = currentDepth.coerceAtLeast(depth)

            weightlessAdjacencyList[id].forEach { v ->
                parent[v] = id
                this.callRecursive(v)
            }
            processedOrder.add(id)
            //Done with this node. Backtracking to previous one.
            currentDepth--
        }.invoke(start)
    }

    fun kosaraju(): List<List<Int>> {
        val reversedGraph: WeightlessAdjacencyList = MutableList<MutableList<Int>>(size) { mutableListOf() }.apply {
            weightlessAdjacencyList.forEachIndexed { u, neighbors ->
                neighbors.forEach { v ->
                    this[v].add(u)
                }
            }
        }
        val topologicalOrder = DFS(reversedGraph).topologicalSort().reversed()
        val stronglyConnectedComponents = mutableListOf<List<Int>>()
        topologicalOrder.forEach { id ->
            if (visited[id])
                return@forEach
            dfs(id)
            stronglyConnectedComponents.add(getAndClearCurrentVisitedIds())
        }
        return stronglyConnectedComponents
    }

    fun topologicalSort(): List<Int> {
        for (i in 0 until size) {
            dfs(i)
        }
        return processedOrder//.reversed() //Reversed depending on the order
    }

    fun getAndClearCurrentVisitedIds() =
        currentVisitedIds.map { it }.also { currentVisitedIds.clear() } // Deep copy not clear return

    fun clearCurrentVisitedIds() {
        currentVisitedIds.clear()
    }

    fun getVisitedDepths() = currentVisitedDepts.map { it }
}
