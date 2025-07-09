package graphClasses

import kotlin.math.min

class FloydWarshall(val graph: AdjacencyList) {
    constructor(graph: Graph) : this(graph.getAdjacencyList())
    constructor(intGraph: IntGraph) : this(intGraph.getAdjacencyList())
    constructor(grid: Grid) : this(grid.getAdjacencyList())

    val n = graph.size
    val distances = Array(n) { DoubleArray(n) { Int.MAX_VALUE.toDouble() } }

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
            val modifiedRow = row.map { if(it == Int.MAX_VALUE.toDouble()) -1.0 else it }
            System.err.println("$i: ${modifiedRow.joinToString(", ")}")
        }
    }
}
