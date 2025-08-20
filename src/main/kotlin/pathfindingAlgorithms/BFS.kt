package pathfindingAlgorithms

import UnweightedAdjacencyList


class BFS(val graph: UnweightedAdjacencyList){

    val parents = IntArray(graph.size) { -1 }

    fun bfs(startIds: List<Int>, targetId: Int, nodesAreDeleted: BooleanArray?) {
        currentVisited.clear()
        distances.fill(Double.MAX_VALUE)
        val queue = java.util.ArrayDeque<Int>()
        startIds.forEach {
            if (nodesAreDeleted?.get(it) == true) {
                System.err.println("Warning: Starting node $it is deleted, cannot perform BFS from it.")
                return@forEach
            }
            queue.add(it)
            distances[it] = 0.0
        }
        while (queue.isNotEmpty()) {
            val currentId = queue.first()
            queue.removeFirst()
            if (visited[currentId])
                continue
            visited[currentId] = true
            currentVisited.add(currentId)
            if (currentId == targetId)
                return
            val currentDistance = distances[currentId]
            graph[currentId].forEach { v ->
                if(nodesAreDeleted?.get(v) == true) return@forEach
                val newDistance = currentDistance + 1
                if (!visited[v] && newDistance < distances[v]) { // Do distance check to avoid re queueing startIds
                    queue.add(v)
                    parents[v] = currentId
                    distances[v] = newDistance
                }
            }
        }
    }
    fun getCurrentVisitedIds() = // Deep Copy
        currentVisited.map { it }
}
