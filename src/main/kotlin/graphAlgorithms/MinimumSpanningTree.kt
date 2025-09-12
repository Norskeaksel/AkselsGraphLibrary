package graphAlgorithms

import AdjacencyList
import Edge
import java.util.*

fun minimumSpanningTree(graph: AdjacencyList): Pair<Double, AdjacencyList> {
    if (graph.size == 0) error("The graph is empty. Cannot do minimumSpanningTree")

    val visited = BooleanArray(graph.size)
    val connections: AdjacencyList = MutableList(graph.size) { mutableListOf() }
    val pq = PriorityQueue<Triple<Double, Int, Int>> { a, b -> a.first.compareTo(b.first) }
    var totalWeight = 0.0

    visited[0] = true
    graph[0].forEach { (weight, to) ->
        pq.add(Triple(weight, 0, to))
    }
    var c = 0
    while (c < graph.size - 1) {
        if (pq.isEmpty()) error("The graph is not fully connected. Cannot do minimumSpanningTree")
        val (w, u, v) = pq.poll()
        if (visited[v]) continue
        visited[v] = true
        c++
        totalWeight += w

        connections[u].add(Edge(w, v))
        connections[v].add(Edge(w, u))

        graph[v].forEach { (weight, next) ->
            if (!visited[next]) {
                pq.add(Triple(weight, v, next))
            }
        }
    }

    return Pair(totalWeight, connections)
}
