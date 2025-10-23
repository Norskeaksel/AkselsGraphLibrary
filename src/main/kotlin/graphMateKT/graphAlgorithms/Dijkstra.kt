package graphMateKT.graphAlgorithms

import graphMateKT.AdjacencyList
import graphMateKT.Edge
import java.util.*

internal class Dijkstra(private val graph: AdjacencyList) {
    private var r = GraphSearchResults(graph.size)
    fun dijkstra(start: Int, previousSearchResults: GraphSearchResults? = null): GraphSearchResults {
        r = previousSearchResults ?: GraphSearchResults(graph.size)
        r.weightedDistances[start] = 0.0
        val pq = PriorityQueue<Edge> { a, b -> a.first.compareTo(b.first) }
        pq.add(Edge(0.0, start))
        while (pq.isNotEmpty()) {
            val u = pq.poll().second
            if (r.visited[u]) continue
            r.visited[u] = true
            r.currentVisited.add(u)
            graph[u].forEach { (d, v) ->
                val newDistance = r.weightedDistances[u] + d
                if (newDistance < r.weightedDistances[v]) {
                    r.weightedDistances[v] = newDistance
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
