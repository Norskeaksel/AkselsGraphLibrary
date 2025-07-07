package examples

import graphClasses.*

// https://open.kattis.com/problems/bookclub
fun main() {
    val ans = bookclub(); _writer.flush()
    println(ans)
}

fun bookclub(): String {
    val (n, m) = readInts(2)
    val maxFlow = MaxFlow(2 * n + 2)
    val source = 2 * n
    val sink = 2 * n + 1
    repeat(n) {
        maxFlow.addWeightlessEdge(source, it)
        maxFlow.addWeightlessEdge(n + it, sink)
    }
    repeat(m) {
        val (a, b) = readInts(2)
        maxFlow.addWeightlessEdge(a, n + b)
    }
    val maxFlowSum = maxFlow
    val matches = maxFlow.runMaxFlow(source, sink)
    System.err.println("Matches: $matches")
    return if (matches == n) {
        "YES"
    } else {
        "NO"
    }
}