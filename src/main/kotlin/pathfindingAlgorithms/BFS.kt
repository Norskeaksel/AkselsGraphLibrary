package pathfindingAlgorithms

import UnweightedAdjacencyList

fun bfs(
    graph: UnweightedAdjacencyList,
    startIds: List<Int>,
    targetId: Int
    nodesAreDeleted: BooleanArray? = null
): GraphSearchResults {
    val r = GraphSearchResults(graph.size)
    val queue = java.util.ArrayDeque<Int>()
    startIds.forEach {
        if (nodesAreDeleted?.get(it) == true) {
            System.err.println("Warning: Starting node $it is deleted, cannot perform BFS from it.")
            return@forEach
        }
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
            if (nodesAreDeleted?.get(v) == true) return@forEach
            val newDistance = currentDistance + 1
            if (!r.visited[v] && newDistance < r.intDistances[v]) { // Do distance check to avoid re queueing startIds
                queue.add(v)
                r.parents[v] = currentId
                r.intDistances[v] = newDistance
            }
        }
    }
    return r
}
