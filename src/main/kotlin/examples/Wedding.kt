package examples

import graphClasses.*

// https://open.kattis.com/problems/wedding
fun main() {
    val ans = wedding(); _writer.flush()
    println(ans)
}

fun wedding(): String {
    val (n, m) = readInts(2)
    val g = Graph()
    repeat(n) {
        val h = "${it + 1}h"
        val w = "${it + 1}w"
        g.connectUnweighted(h, w)
    }
    repeat(m) {
        val (a, b) = readStrings(2)
        g.connectUnweighted(a, b)
    }
    val xorClauses = g.connections()
    return ""
}
