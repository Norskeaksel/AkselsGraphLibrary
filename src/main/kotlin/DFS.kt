import kotlin.math.max

class DFS(val graph: AdjacencyList) {
    constructor(grid: Grid) : this(grid.getAdjacencyList())

    val size = graph.size
    var visited = BooleanArray(size)
    var prossessed = mutableListOf<Int>()
    var depth = 0
    private var currentVisitedDepts = mutableListOf<Int>()
    val parent = IntArray(graph.size) { -1 }
    private var currentVisitedIds = mutableListOf<Int>()

    fun dfsSimple(startId: Int) {
        if (startId in currentVisitedIds) // O(n) operation, could be optimized to O(1)
            return
        currentVisitedIds.add(startId)
        graph[startId].forEach { (_, connectedNodeId) ->
            dfsSimple(connectedNodeId)
        }
    }

    fun dfsIterative(startId: Int) {
        clearCurrentVisited()
        val stack = ArrayDeque<Int>()
        stack.add(startId)
        while (stack.isNotEmpty()) {
            val currentId = stack.last()
            stack.removeLast()
            if (visited[currentId])
                continue

            visited[currentId] = true
            currentVisitedIds.add(currentId)
            graph[currentId].forEach { (d, v) ->
                if (!visited[v]) {
                    parent[v] = startId
                    stack.add(v)
                }
            }
        }
        depth = currentVisitedIds.size
    }

    fun dfsRecursive(start: Int) {
        var currentDepth = 0
        clearCurrentVisited()
        DeepRecursiveFunction<Int, Unit> { id ->
            if (visited[id])
                return@DeepRecursiveFunction
            visited[id] = true
            // Just visited this node
            currentVisitedIds.add(id)
            currentVisitedDepts.add(currentDepth)
            currentDepth++
            depth = max(depth, currentDepth)
            graph[id].forEach { (d, v) ->
                parent[v] = id
                this.callRecursive(v)
            }
            //Done with this node. Backtracking to previous one.
            currentDepth--
            prossessed.add(id)
        }.invoke(start)
    }

    fun topologicalSort(): List<Int> {
        for (i in 0 until size) {
            dfsRecursive(i)
        }
        return prossessed.reversed() //Reversed depending on the order
    }

    fun getCurrentVisited() = // Deep Copy
        currentVisitedIds.map { it }

    fun clearCurrentVisited() {
        currentVisitedIds.clear()
    }

    fun getVisitedDepths() = currentVisitedDepts.map { it }
}
