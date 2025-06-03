package examples

import graphClasses.*

// https://open.kattis.com/problems/baas

fun main() {
    val ans =baas(); _writer.flush()
    println(ans)
}

fun baas(): Long {
    val n = readInt()
    val intGraph = IntGraph(n)
    val stepTime = readInts(n)
    repeat(n) { step ->
        val ci = readInt()
        repeat(ci) {
            val a = readInt() - 1
            intGraph.addEdge(a, step, stepTime[a])
        }
    }
    val topologicalOrder = intGraph.topologicalOrder()
    System.err.println(topologicalOrder)
    return 0L
}