package examples

import graphAlgorithms.Clauses
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
    g.addClause { "0w" or "0w" }
    g.addClause { "0h" nand "0h" }
    repeat(n) {
        val h = "${it}h"
        val w = "${it}w"
        g.connectUnweighted(h, w)
        g.addClause { h xor w }
    }
    repeat(m) {
        val (a, b) = readStrings(2)
        g.addClause {a or b}
    }
    val (_, _, truthmap) = g.twoSat() ?: return "bad luck"
    val ans =  truthmap.filter { it.key != "0w" && it.value }.keys.joinToString(" ")
    return ans
}
