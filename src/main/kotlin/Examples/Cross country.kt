package Examples
// https://open.kattis.com/problems/crosscountry?tab=metadata
import Dijkstra
import IntGraph
import _writer
import readDoubles
import readInts
import java.io.PrintWriter

fun main() {
    _writer.execute(); _writer.flush()
}

fun PrintWriter.execute() {
    val (n, s, t) = readInts(3)
    val graph = IntGraph(n)
    repeat(n){i ->
        val nodes = readDoubles(n)
        nodes.forEachIndexed{ j, d ->
            graph.addEdge(i,j,d)
        }
    }
    val dijkstra = Dijkstra(graph.getAdjacencyList())
    dijkstra.dijkstra(s)
    println(dijkstra.distance[t].toInt())
}