package pathfindingAlgorithms

import AdjacencyList
import Edge
import java.util.*

class Dijkstra(private val graph: AdjacencyList) {
    private fun resetDistances() = distances.fill(Double.POSITIVE_INFINITY)
    private fun resetParents() = parents.fill(-1)
    private fun clearCurrents() {
        // currentVisited = mutableListOf()
        // currentVisitedDistances = mutableListOf()
        visited.fill(false)
    }

    fun dijkstra(start: Int, target:Int = -1) {
        resetDistances()
        resetParents()
        clearCurrents()
        distances[start] = 0.0
        val pq = PriorityQueue<Edge> { a, b -> a.first.compareTo(b.first) }
        pq.add(Edge(0.0, start))
        while (pq.isNotEmpty()) {
            val u = pq.poll().second
            if (visited[u]) continue
            visited[u] = true
            if(u == target)
                return
            // currentVisited.add(u)
            // currentVisitedDistances.add(distances[u])
            graph[u].forEach { e ->
                updateDistAndQueueIfUnvisited(u, e, pq)
            }
        }
    }
    private fun updateDistAndQueueIfUnvisited(u: Int, e: Edge, pq: PriorityQueue<Edge>) {
        val newDist = distances[u] + e.first
        val oldDist = distances[e.second]
        if (newDist < oldDist) {
            distances[e.second] = newDist
            parents[e.second] = u
            if (!visited[e.second])
                pq.add(Edge(newDist, e.second))
        }
    }
}
