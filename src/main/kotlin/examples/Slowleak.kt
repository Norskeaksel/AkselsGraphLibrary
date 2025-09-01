package examples

import graphClasses.*
import graphGraphics.visualizeSearch
import pathfindingAlgorithms.FloydWarshall
import pathfindingAlgorithms.GraphSearchResults.Companion.INF

// https://open.kattis.com/problems/slowleak
fun main() {
    val ans = slowleak(); _writer.flush()
    println(ans)
}

fun slowleak(): String {
    val (n, m, t, d) = readInts(4)
    val g = IntGraph(n + 1)
    val start = 1
    val goal = n
    val repairStations = readInts(t)
    repeat(m) {
        val (i, j, k) = readInts(3)
        g.connect(i, j, k.toDouble())
    }
    g.floydWarshall()
    val compressedGraphNodes = repairStations + listOf(start, goal)
    val compressedGraph = IntGraph(n + 1)
    for (i in compressedGraphNodes.indices) {
        for (j in i + 1 until compressedGraphNodes.size) {
            val u = compressedGraphNodes[i]
            val v = compressedGraphNodes[j]
            val w = g.distanceFromUtoV(u, v)
            if (w <= d)
                compressedGraph.connect(u, v, w)
        }
    }
    compressedGraph.dijkstra(start, goal)
    val finalDistance = compressedGraph.distanceTo(goal)
    return if (finalDistance == INF)
        "stuck"
    else
        compressedGraph.distanceTo(goal).toInt().toString()
}