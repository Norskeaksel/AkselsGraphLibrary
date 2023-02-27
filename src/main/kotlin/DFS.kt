class DFS(val graph: AdjacencyList) {
    val size = graph.size
    var visited = IntArray(size)
    var currentVisited = mutableListOf<Int>()

    fun dfsIterative(startId:Int): List<Int> {
        val stack = ArrayDeque<Int>()
        stack.add(startId)
        val currentVisited = mutableListOf<Int>()
        while (stack.isNotEmpty()) {
            val currentId = stack.last()
            stack.removeLast()
            if(visited[currentId] == 1)
                continue

            visited[currentId] = 1
            currentVisited.add(currentId)
            graph[currentId].forEach { (d, v) ->
                if (visited[v] == 0) {
                    stack.add(v)
                }
            }
        }
        return currentVisited
    }

    fun dfsRecursive(start: Int): List<Int> {
        val currentVisited = mutableListOf<Int>()
        DeepRecursiveFunction<Int, Unit> { id ->
            visited[id] = 1
            currentVisited.add(id)
            graph[id].forEach {(d, v) ->
                if (visited[v] == 0) {
                    this.callRecursive(v)
                }
            }
        }.invoke(start)
        return currentVisited
    }
}