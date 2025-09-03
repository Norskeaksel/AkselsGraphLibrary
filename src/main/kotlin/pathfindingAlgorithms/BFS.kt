package pathfindingAlgorithms

import UnweightedAdjacencyList

class BFS(private val graph: UnweightedAdjacencyList) {
    fun bfs(
        startIds: List<Int>,
        targetId: Int = -1,
        previousSearchResult: GraphSearchResults? = null,
    ): GraphSearchResults {
        val r = previousSearchResult ?: GraphSearchResults(graph.size)
        r.currentVisited.clear()
        val queue = ArrayDeque<Int>()
        startIds.forEach {
            queue.add(it)
            r.intDistances[it] = 0
        }
        while (queue.isNotEmpty()) {
            val currentId = queue.removeFirst()
            if (r.visited[currentId])
                continue
            r.visited[currentId] = true
            r.currentVisited.add(currentId)

            val currentDistance = r.intDistances[currentId]
            graph[currentId].forEach { v ->
                val newDistance = currentDistance + 1
                if ((!r.visited[v] && newDistance < r.intDistances[v]) || v == targetId) {
                    r.parents[v] = currentId
                    r.depth = newDistance.coerceAtLeast(r.depth)
                    r.intDistances[v] = newDistance
                    if (v == targetId){
                        r.foundTarget = true
                        return r
                    }
                    queue.add(v)
                }
            }
        }
        r.processedOrder = r.currentVisited
        return r
    }
}
