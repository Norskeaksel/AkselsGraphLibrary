package examples

import graphClasses.*
import readInts
import readStrings

fun main() {
    val ans = wedding()
    println(ans)
    System.out.flush()
}

/** Solves https://open.kattis.com/problems/wedding */
fun wedding(): String {
    val (n, m) = readInts(2)
    val g = ClauseGraph()
    repeat(n) {
        val h = "${it}h"
        val w = "${it}w"
        g.addClause { h Xor w }
    }
    g.setTrue("0w")
    g.setTrue(!"0h")
    repeat(m) {
        val (a, b) = readStrings(2)
        g.addClause { a Or b }
    }
    val (_, truthmap) = g.twoSat() ?: return "bad luck"
    val ans = truthmap.filter { it.key != "0w" && it.value }.keys.joinToString(" ")
    return ans
}
