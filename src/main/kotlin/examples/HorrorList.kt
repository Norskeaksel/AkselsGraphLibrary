package examples

import graphClasses.*
import readInts

// https://open.kattis.com/problems/horror
fun main() {
    val ans = horrorList()
    println(ans)
}

fun horrorList(): Int {
    val (n, h, l) = readInts(3)
    val intGraph = IntGraph(n)
    val startIds = readInts(h)
    repeat(l) {
        val (u, v) = readInts(2)
        intGraph.connectUnweighted(u, v)
    }
    intGraph.bfs(startIds)
    return intGraph.furthestNode()
}
