class DFS(val graph: Graph) {
    val adjacencyList = graph.getAdjacencyList()
    val size = graph.size()
    var visited = IntArray(size)

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
            adjacencyList[currentId].forEach { (d, v) ->
                if (visited[v] == 0) {
                    stack.add(v)
                }
            }
        }
        return currentVisited
    }

    private fun dfs(id:Int, currentVisited:MutableList<Int>){
        visited[id] = 1
        currentVisited.add(id)
        adjacencyList[id].forEach { (d, v) ->
            if (visited[v] == 0)
                dfs(v, currentVisited)
        }
    }

    fun dfsRecursive(startId:Int): List<Int> {
        val currentVisited = mutableListOf<Int>()
        dfs(startId, currentVisited)
        return currentVisited
    }
}
