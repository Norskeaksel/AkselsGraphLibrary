package pathfindingAlgorithms

import UnweightedAdjacencyList

class BFS(private val graph: UnweightedAdjacencyList) {
    fun bfs(
        startIds: List<Int>,
        targetId: Int = -1,
        previousSearchResult: GraphSearchResults? = null,
    ): GraphSearchResults {
        val r = previousSearchResult ?: GraphSearchResults(graph.size)
        val queue = java.util.ArrayDeque<Int>()
        startIds.forEach {
            queue.add(it)
            r.intDistances[it] = 0
        }
        while (queue.isNotEmpty()) {
            val currentId = queue.first()
            queue.removeFirst()
            if (r.visited[currentId])
                continue
            r.visited[currentId] = true
            r.currentVisited.add(currentId)
            if (currentId == targetId)
                return r
            val currentDistance = r.intDistances[currentId]
            graph[currentId].forEach { v ->
                val newDistance = currentDistance + 1
                if (!r.visited[v] && newDistance < r.intDistances[v]) { // Do distance check to avoid re queueing startIds
                    queue.add(v)
                    r.parents[v] = currentId
                    r.intDistances[v] = newDistance
                    r.depth = newDistance.coerceAtLeast(r.depth)
                }
            }
        }
        return r
    }
}