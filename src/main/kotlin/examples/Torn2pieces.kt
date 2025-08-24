package examples

import graphClasses.*

// https://open.kattis.com/problems/torn2pieces
fun main() {
    val ans = torn2pieces(); _writer.flush()
    println(ans)
}

fun torn2pieces(): String {
    val n = readInt()
    val graph = Graph()
    repeat(n) {
        val stations = readString().split(" ").toMutableList()
        val fromStation = stations.removeFirst()
        stations.forEach { toStation ->
            graph.connect(fromStation, toStation)
        }
    }
    val (start, end) = readString().split(" ")
    graph.addNode(start)
    graph.addNode(end)
    graph.bfs(start, end)
    val path = graph.getPath(end)
    return if (path.size == 1) {
        "no route found"
    } else {
        path.joinToString(" ")
    }
}
