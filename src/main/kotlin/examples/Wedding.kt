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
    val g = Graph()
    val clauses = Clauses()
    clauses.add { "0w" or "0w" }
    clauses.add { "0h" nand "0h" }
    repeat(n) {
        val h = "${it}h"
        val w = "${it}w"
        g.connectUnweighted(h, w)
        clauses.add { h xor w }
    }
    repeat(m) {
        val (a, b) = readStrings(2)
        clauses.add {a or b}
    }
    val (_, _, truthmap) = g.twoSat(clauses) ?: return "bad luck"
    val ans =  truthmap.filter { it.key != "0w" && it.value }.keys.joinToString(" ")
    return ans
}
