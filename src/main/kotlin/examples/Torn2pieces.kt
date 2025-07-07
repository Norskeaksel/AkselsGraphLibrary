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
    val startId = graph.node2id(start) ?: run {
        graph.addNode(start)
        graph.node2id(start)!!
    }
    val endId = graph.node2id(end) ?: run {
        graph.addNode(end)
        graph.node2id(end)!!
    }
    val bfs = BFS(graph)
    bfs.bfs(startId, endId)
    val path = getPath(endId, bfs.parents).map { graph.id2Node(it) }
    return if (path.size == 1){
        "no route found"
    }
    else{
        path.joinToString(" ")
    }
}
