package graphMateKT.examples

import graphMateKT.Not
import graphMateKT.graphClasses.ClauseGraph
import graphMateKT.not
import graphMateKT.readInts

fun main(){
    val ans = amanda()
    println(ans)
    System.out.flush()
}

/** Solves https://open.kattis.com/problems/amanda */
fun amanda(): String {
    val (n, m) = readInts(2)
    val nodeValues = Array<Boolean?>(n + 1) { null }
    val g = ClauseGraph()

    repeat(m) {
        val (a, b, c) = readInts(3)
        val value = when (c) {
            0 -> false
            2 -> true
            else -> null
        }

        if (value == null) {
            g.addClause { a Xor b }
        } else {
            val prevA = nodeValues[a]
            if (prevA != null && prevA != value) return "impossible"
            nodeValues[a] = value

            val prevB = nodeValues[b]
            if (prevB != null && prevB != value) return "impossible"
            nodeValues[b] = value

            if (value) {
                g.addClause { a Or a }
                g.addClause { b Or b }
            } else {
                g.addClause { !a Or !a }
                g.addClause { !b Or !b }
            }
        }
    }

    val truthMap = mutableMapOf<Any, Boolean>().apply {
        nodeValues.forEachIndexed { index, value ->
            if (value != null) {
                this[index] = value
                this[!index] = !value
            }
        }
    }

    val (scc, _) = g.twoSat() ?: return "impossible"

    val givenSCC = scc.filter { component ->
        component.any { it in truthMap.keys }
    }
    val unsetSCC = scc.filter { component ->
        component.all { it !in truthMap.keys }
    }
    unsetSCC.forEach { component ->
        if (component.filterIsInstance<Int>().count() > component.filterIsInstance<Not>().count()) {
            component.forEach { truthMap[it] = false; truthMap[!it] = true }
        } else {
            component.forEach { truthMap[it] = true; truthMap[!it] = false }
        }
    }
    givenSCC.forEach { component ->
        if (component.any { truthMap[it] == true }) {
            component.forEach { truthMap[it] = true }
        }
    }
    val ans = truthMap.filter { it.key is Int }.count { it.value }
    return ans.toString()
}