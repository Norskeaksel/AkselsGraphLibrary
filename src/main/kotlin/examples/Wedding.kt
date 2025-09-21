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
    repeat(n-1) {
        val h = "${it+1}h"
        val w = "${it+1}w"
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
    val (truthmap, sccs) = g.twoSat(orClauses,  xorClauses) ?: return "bad luck"
    debug("truthmap: $truthmap")
    debug("sccs: $sccs")
    val ans =  truthmap.filter { it.value }.keys.joinToString(" ")
    debug("ans: $ans")
    return ans
}
