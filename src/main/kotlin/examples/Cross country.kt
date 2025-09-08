package examples
// https://open.kattis.com/problems/crosscountry?tab=metadata
import graphClasses.IntGraph
import graphClasses._writer
import graphClasses.readDoubles
import graphClasses.readInts
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
    graph.dijkstra(s)
    println(graph.doubleDistanceTo(t).toInt())
}