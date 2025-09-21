package examples

import Clauses
import graphAlgorithms.twoSat
import readInts

// https://open.kattis.com/problems/amanda
fun main() {
    val ans = amanda()
    println(ans)
}

fun amanda(): String {
    val (n, m) = readInts(2)
    val nodeValues = Array<Boolean?>(n + 1) { null }
    val xorClauses = mutableListOf<Pair<Int, Int>>()
    repeat(m) {
        val (a, b, c) = readInts(3)
        val value = when (c) {
            0 -> false
            2 -> true
            else -> null
        }
        nodeValues[a] = value
        nodeValues[b] = value
        if (value == null) {
            xorClauses.add(a to b)
        }
    }
    val truthMap = mutableMapOf<Int, Boolean>().apply {
        nodeValues.forEachIndexed { index, value ->
            if (value != null) {
                this[index] = value
                this[-index] = !value
            }
        }
    }
    val (finalTruthMap, scc) = twoSat(xorClauses = xorClauses, truthMap = truthMap) ?: return "impossible"
    return finalTruthMap.count { it.value && it.key > 0 }.toString()
}