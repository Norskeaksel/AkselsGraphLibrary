package pathfindingAlgorithms

import WeightlessAdjacencyList

class BFS(val graph: WeightlessAdjacencyList) {

    private val d = 1.0
    private val size = graph.size
    var visited = BooleanArray(size)
    val distances = DoubleArray(size)
    private var currentVisited = mutableListOf<Int>()
    private var currentVisitedDistances = mutableListOf<Double>()
    val parents = IntArray(graph.size) { -1 }

    fun bfs(startIds: List<Int>, targetId: Int = -1) {
        currentVisited.clear()
        distances.fill(Double.MAX_VALUE)
        val queue = java.util.ArrayDeque<Int>()
        startIds.forEach {
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
    fun getCurrentVisitedIds() = // Deep Copy
        currentVisited.map { it }
}
