package examples

import debug
import graphClasses.*
import graphGraphics.visualizeComponents
import graphGraphics.visualizeSearch
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
    repeat(n) {
        val h = "${it}h"
        val w = "${it}w"
        g.connectUnweighted(h, w)
    }
    val xorClauses = g.connections()
    val orClauses = mutableListOf<Pair<Any, Any>>("0w" to "0w")
    repeat(m) {
        val (a, b) = readStrings(2)
        orClauses.add(a to b)
    }
    debug("xorClauses: $xorClauses")
    debug("orClauses: $orClauses")
    val (_, sccs, truthmap) = g.twoSat(orClauses,  xorClauses) ?: return "bad luck"
    debug("truthmap: $truthmap")
    debug("sccs: $sccs")
    val ans =  truthmap.filter { it.key != "0w" && it.value }.keys.joinToString(" ")
    debug("ans: $ans")
    return ans
}
