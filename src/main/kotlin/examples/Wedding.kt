package examples

import debug
import graphClasses.*
import graphGraphics.visualizeComponents
import readInts
import readStrings

// https://open.kattis.com/problems/wedding
fun main() {
    val ans = wedding()
    println(ans)
}

fun wedding(): String {
    val (n, m) = readInts(2)
    val g = Graph()
    val initialCondition = mapOf<Any, Boolean>("0h" to false, "0h" to true)
    repeat(n) {
        val h = "${it}h"
        val w = "${it}w"
        g.connectUnweighted(h, w)
    }
    val xorClauses = g.connections()
    val orClauses = mutableListOf<Pair<Any, Any>>()
    repeat(m) {
        val (a, b) = readStrings(2)
        orClauses.add(a to b)
    }
    debug("xorClauses: $xorClauses")
    debug("orClauses: $orClauses")
    val (truthmap, sccs) = g.twoSat(orClauses,  xorClauses, initialTruthMap = initialCondition) ?: return "bad luck"
    debug("truthmap: $truthmap")
    return truthmap.filter { it.value }.keys.joinToString(" ")
}
