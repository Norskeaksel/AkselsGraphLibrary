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
    val dfs = DFS(intGraph)
    val ids = dfs.topologicalSort()
    println(ids)
    return 0L
}