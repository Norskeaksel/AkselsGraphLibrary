import kotlin.math.max

class DFS(val graph: AdjacencyList) {
    val size = graph.size
    var visited = IntArray(size)
    var depth = 0
    private var currentVisited = mutableListOf<Int>()

    fun dfsIterative(startId: Int) {
        depth = 0
        currentVisited.clear()
        val stack = ArrayDeque<Int>()
        stack.add(startId)
        while (stack.isNotEmpty()) {
            val currentId = stack.last()
            stack.removeLast()
            if (visited[currentId] == 1)
                continue

            visited[currentId] = 1
            currentVisited.add(currentId)
            graph[currentId].forEach { (d, v) ->
                if (visited[v] == 0) {
                    stack.add(v)
                }
            }
        }
    }

    fun dfsRecursive(start: Int) {
        var currentDepth = 0
        currentVisited.clear()
        DeepRecursiveFunction<Int, Unit> { id ->
            visited[id] = 1
            currentVisited.add(id)
            currentDepth++
            depth = max(depth, currentDepth)
            graph[id].forEach { (d, v) ->
                if (visited[v] == 0) {
                    this.callRecursive(v)
                }
            }
            currentDepth--
        }.invoke(start)
    }
    fun getCurrentVisited() = // Deep Copy
        currentVisited.map { it }.toList()
}
