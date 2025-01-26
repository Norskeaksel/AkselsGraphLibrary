import kotlin.math.max
import kotlin.math.min

class DFS(val graph: AdjacencyList) {
    constructor(grid: Grid) : this(grid.getAdjacencyList())

    val size = graph.size
    var visited = BooleanArray(size)
    var prossessed = mutableListOf<Int>()
    var depth = 0
    var depths = IntArray(size) { Int.MAX_VALUE }
    private var currentVisited = mutableListOf<Int>()
    private var currentVisitedDepts = mutableListOf<Int>()
    val parent = IntArray(graph.size) { -1 }

    fun dfsSimple(startId: Int, depth: Int = 0) {
        if (visited[startId])
            return
        visited[startId] = true
        currentVisited.add(startId)
        currentVisitedDepts.add(depth)
        depths[startId] = min(depths[startId], depth)
        graph[startId].forEach { (_, v) ->
            parent[v] = startId
            dfsSimple(v, depth + 1)
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
            currentVisited.add(currentId)
            graph[currentId].forEach { (d, v) ->
                if (!visited[v]) {
                    parent[v] = startId
                    stack.add(v)
                }
            }
        }
        depth = currentVisited.size
    }

    fun dfsRecursive(start: Int) {
        var currentDepth = 0
        clearCurrentVisited()
        DeepRecursiveFunction<Int, Unit> { id ->
            if (visited[id])
                return@DeepRecursiveFunction
            visited[id] = true
            // Just visited this node
            currentVisited.add(id)
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
        currentVisited.map { it }

    fun clearCurrentVisited() {
        currentVisited.clear()
    }

    fun getVisitedDepths() = currentVisitedDepts.map { it }
}
