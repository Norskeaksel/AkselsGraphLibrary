package examples
// https://open.kattis.com/problems/crosscountry?tab=metadata
import graphClasses.IntGraph
import readDoubles
import readInts

fun main() {
    val ans = crossCountry()
    println(ans)
}

fun crossCountry(): Int {
    val (n, s, t) = readInts(3)
    val graph = IntGraph(n)
    repeat(n){i ->
        val nodes = readDoubles(n)
        nodes.forEachIndexed{ j, d ->
            graph.addWeightedEdge(i,j,d)
        }
    }
    graph.dijkstra(s)
    return graph.weightedDistanceTo(t).toInt()
}