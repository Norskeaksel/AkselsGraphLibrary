package pathfindingAlgorithms

import AdjacencyList
import Edge
import java.util.*

class Dijkstra(private val graph: AdjacencyList) {
    private var r = GraphSearchResults(graph.size)
    fun dijkstra(start: Int, target: Int = -1, previousSearchResults: GraphSearchResults? = null): GraphSearchResults {
        r = previousSearchResults ?: GraphSearchResults(graph.size)
        r.doubleDistances[start] = 0.0
        val pq = PriorityQueue<Edge> { a, b -> a.first.compareTo(b.first) }
        pq.add(Edge(0.0, start))
        while (pq.isNotEmpty()) {
            val u = pq.poll().second
            if (r.visited[u]) continue
            r.visited[u] = true
            r.currentVisited.add(u)
            if (u == target)
                return r
            graph[u].forEach { (d, v) ->
                val newDistance = r.doubleDistances[u] + d
                if (newDistance < r.doubleDistances[v]) {
                    r.doubleDistances[v] = newDistance
                    r.parents[v] = u
                    if (!r.visited[v]) {
                        pq.add(Edge(newDistance, v))
                    }
                }
            }
        }
        r.processedOrder = r.currentVisited
        return r
    }
}
