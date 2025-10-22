package examples

import graphClasses.*
import readInts

// Solves https://open.kattis.com/problems/slowleak
fun main() {
    val ans = slowleak()
    println(ans)
}

fun slowleak(): String {
    val (n, m, t, d) = readInts(4)
    val g = Graph()
    val start = 1
    val goal = n
    val repairStations = readInts(t)
    repeat(m) {
        val (i, j, k) = readInts(3)
        g.connect(i, j, k.toDouble())
    }
    g.floydWarshall()
    val compressedGraphNodes = repairStations + listOf(start, goal)
    val compressedGraph = Graph()
    compressedGraphNodes.forEach { node ->
        compressedGraph.addNode(node)
    }
    for (i in compressedGraphNodes.indices) {
        for (j in i + 1 until compressedGraphNodes.size) {
            val u = compressedGraphNodes[i]
            val v = compressedGraphNodes[j]
            val w = g.distanceFromUtoV(u, v)
            if (w <= d)
                compressedGraph.connect(u, v, w)
        }
    }
    compressedGraph.dijkstra(start)
    val finalDistance = compressedGraph.distanceTo(goal)
    return if (finalDistance == Double.POSITIVE_INFINITY)
        "stuck"
    else
        finalDistance.toInt().toString()
}