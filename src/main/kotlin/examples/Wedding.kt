package examples

import graphClasses.*
import readInts
import readStrings

// https://open.kattis.com/problems/wedding
fun main() {
    val ans = wedding()
    println(ans)
}

fun wedding(): String {
    val (n, m) = readInts(2)
    val g = DependencyGraph()
    repeat(n) {
        val h = "${it}h"
        val w = "${it}w"
        g.addNode(h)
        g.addNode(w)
        g.addClause { h Xor w }
    }
    g.addClause { "0w" Or "0w" }
    g.addClause { !"0h" Or !"0h" }
    repeat(m) {
        val (a, b) = readStrings(2)
        g.addClause { a Or b }
    }
    val (_, truthmap) = g.twoSat() ?: return "bad luck"
    val ans = truthmap.filter { it.key != "0w" && it.value }.keys.joinToString(" ")
    return ans
}
