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
    val truthMap = mutableMapOf<Int, Boolean?>().apply {
        nodeValues.forEachIndexed { index, value ->
                this[index] = value
                this[-index] = value?.not()
        }
    }
    val (_, scc) = twoSat(clauses, emptyMap()) ?: return "impossible"
    debug(truthMap)
    debug(scc)
    val fixedSCCs = scc.filter { component ->
        component.any { truthMap[it] == true }
    }
    val fixedTruths = fixedSCCs.flatten().filter { it > 0 }.size
    val flexibleSCCs = scc.filter { component ->
        component.all { truthMap[it] == null }
    }
    val minmalTruths = flexibleSCCs.fold(0) { acc, component ->
        val nrOfPositiveNodes = component.count { it > 0 }
        val nrOfNegativeNodes = component.count { it < 0 }
        acc + minOf(nrOfPositiveNodes, nrOfNegativeNodes)
    }


    return (minmalTruths+fixedTruths).toString()
}