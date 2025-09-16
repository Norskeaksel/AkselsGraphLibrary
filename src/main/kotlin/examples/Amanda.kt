package examples

import Clauses
import graphAlgorithms.twoSat
import graphClasses.*

// https://open.kattis.com/problems/amanda
fun main() {
    val ans = amanda(); _writer.flush()
    println(ans)
}

fun amanda(): String {
    val (n, m) = readInts(2)
    val nodeValues = Array<Boolean?>(n + 1) { null }
    val clauses: Clauses = mutableListOf()
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
            clauses.add(a to b)
            clauses.add(-b to -a)
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
    val (_, scc) = twoSat(clauses, truthMap, false) ?: return "impossible"
    return "0"
}