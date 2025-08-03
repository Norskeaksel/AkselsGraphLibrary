package examples

import graphClasses.*
import pathfindingAlgorithms.Dijkstra
import kotlin.math.ceil
import kotlin.math.roundToInt

const val ITEM_BOOST = 1E-9

fun main() {
    val ans = bigtruck(); _writer.flush()
    println(ans)
}

fun bigtruck(): String {
    val n = readInt()
    val graph = IntGraph(n + 1)
    val items = listOf(0) + readString().split(" ").map { it.toInt() }
    val m = readInt()
    repeat(m) {
        val (a, b, d) = readInts(3)
        graph.addEdge(a, b, d - ITEM_BOOST * items[b])
        graph.addEdge(b, a, d - ITEM_BOOST * items[a])
    }
    val dijkstra = Dijkstra(graph)
    dijkstra.dijkstra(1)
    val distances = dijkstra.distances
    if (distances[n] == Double.POSITIVE_INFINITY) {
        return "impossible"
    }
    val nrOfItems = ((ceil(distances[n]) - distances[n]) / ITEM_BOOST).roundToInt() + items[1]
    return "${distances[n].roundToInt()} $nrOfItems"
}