package pathfindingAlgorithms

import AdjacencyList
import pathfindingAlgorithms.GraphSearchResults.Companion.INF
import kotlin.math.min

class FloydWarshall(val graph: AdjacencyList) {
    val n = graph.size
    val distances = Array(n) { DoubleArray(n) { INF } }

    init {
        graph.forEachIndexed { u, edges ->
            distances[u][u] = 0.0
            edges.forEach { (d, v) ->
                distances[u][v] = d
            }
        }
    }

    fun floydWarshall() {
        repeat(n) { k ->
            repeat(n) { i ->
                repeat(n) { j ->
                    distances[i][j] = min(distances[i][j], distances[i][k] + distances[k][j])
                }
            }
        }
    }
    fun printDistances() {
        distances.forEachIndexed { i, row ->
            System.err.println("$i: ${row.joinToString(", ")}")
        }
    }
}
