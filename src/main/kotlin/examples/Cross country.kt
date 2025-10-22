package examples
import graphClasses.IntGraph
import readDoubles
import readInts

fun main() {
    val ans = crossCountry()
    println(ans)
    System.out.flush()
}

/** Solves https://open.kattis.com/problems/crosscountry?tab=metadata */
fun crossCountry(): Int {
    val (n, s, t) = readInts(3)
    val graph = IntGraph(n)
    repeat(n){i ->
        val nodes = readDoubles(n)
        nodes.forEachIndexed{ j, d ->
            graph.addEdge(i,j,d)
        }
    }
    graph.dijkstra(s)
    return graph.distanceTo(t).toInt()
}