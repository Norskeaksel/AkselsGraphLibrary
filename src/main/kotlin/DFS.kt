class DFS(val graph: AdjacencyList) {
    val size = graph.size
    var visited = BooleanArray(size)
    var depth = 0
    private var currentVisited = mutableListOf<Int>()

    fun dfsIterative(startId: Int) {
        currentVisited.clear()
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
                    stack.add(v)
                }
            }
        }
    }

    fun dfsRecursive(start: Int) {
        var currentDepth = 0
        currentVisited.clear()
        DeepRecursiveFunction<Int, Unit> { id ->
            visited[id] = true
            currentVisited.add(id)
            currentDepth++
            depth = if(depth > currentDepth) depth else currentDepth
            graph[id].forEach { (d, v) ->
                if (!visited[v]) {
                    this.callRecursive(v)
                }
            }
        }.invoke(start)
    }
    fun getCurrentVisited() = // Deep Copy
        currentVisited.map { it }.toList()
}
