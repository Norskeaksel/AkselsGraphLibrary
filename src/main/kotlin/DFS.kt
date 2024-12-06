class DFS(val graph: AdjacencyList) {
    val size = graph.size
    var visited = BooleanArray(size)
    var prossessed = mutableListOf<Int>()
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
        depth = currentVisited.size
    }

    fun dfsRecursive(start: Int) {
        currentVisited.clear()
        DeepRecursiveFunction<Int, Unit> { id ->
            if(visited[id])
                return@DeepRecursiveFunction
            visited[id] = true
            currentVisited.add(id)
            graph[id].forEach { (d, v) ->
                if (!visited[v]) {
                    this.callRecursive(v)
                }
            }
            prossessed.add(id)
        }.invoke(start)
        depth = currentVisited.size
    }

    fun topologicalSort(): List<Int> {
        for(i in 0 until size){
            dfsRecursive(i)
        }
        return prossessed.reversed() //Reversed depending on the order
    }
    fun getCurrentVisited() = // Deep Copy
        currentVisited.map { it }.toList()
}
