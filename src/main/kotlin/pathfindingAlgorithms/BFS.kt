package pathfindingAlgorithms

import graphClasses.WeightlessAdjacencyList

class BFS(val graph: WeightlessAdjacencyList) {

    private val d = 1
    private val size = graph.size
    var visited = BooleanArray(size)
    val distances = IntArray(size)
    private var currentVisited = mutableListOf<Int>()
    private var currentVisitedDistances = mutableListOf<Int>()
    val parents = IntArray(graph.size) { -1 }

    fun bfs(startIds: List<Int>, targetId: Int = -1) {
        currentVisited.clear()
        distances.fill(Int.MAX_VALUE)
        val queue = java.util.ArrayDeque<Int>()
        startIds.forEach {
            queue.add(it)
            distances[it] = 0
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
            currentVisitedDistances.add(currentDistance)
            graph[currentId].forEach { v ->
                val newDistance = currentDistance + d
                if (!visited[v] && newDistance < distances[v]) { // Do distance check to avoid re queueing startIds
                    queue.add(v)
                    parents[v] = currentId
                    distances[v] = newDistance
                }
            }
        }
    }
    fun bfs(startId: Int, targetId: Int = -1) = bfs(listOf(startId), targetId)

    fun getCurrentVisitedIds() = // Deep Copy
        currentVisited.map { it }
}
