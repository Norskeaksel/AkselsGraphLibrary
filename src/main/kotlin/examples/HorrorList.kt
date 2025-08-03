package examples

import graphClasses.*
import pathfindingAlgorithms.BFS

// https://open.kattis.com/problems/horror?editresubmit=17527573
fun main() {
    val ans = horrorList(); _writer.flush()
    println(ans)
}

fun horrorList(): Int {
    val (n, h, l) = readInts(3)
    val intGraph = IntGraph(n)
    val startIds = readInts(h).sorted()
    repeat(l) {
        val (u, v) = readInts(2)
        intGraph.connectWeightless(u, v)
    }
    intGraph.bfs(startIds)
    val maxDist = intGraph.distances.let { d -> d.indices.maxBy { d[it] } }
    return maxDist
}
