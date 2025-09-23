package examples

import graphAlgorithms.twoSat
import map2Ints
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

        if (value == null) {
            xorClauses.add(a to b)
        } else {
            val prevA = nodeValues[a]
            if (prevA != null && prevA != value) return "impossible"
            nodeValues[a] = value

            val prevB = nodeValues[b]
            if (prevB != null && prevB != value) return "impossible"
            nodeValues[b] = value
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
    val (_, scc) = twoSat(xorClauses = xorClauses, truthMap = truthMap) ?: return "impossible"
    val givenSCC = scc.filter { component ->
        component.any { it in truthMap.keys }
    }.map2Ints()
    val unsetSCC = scc.filter { component ->
        component.all { it !in truthMap.keys }
    }.map2Ints()
    unsetSCC.forEach { component ->
        if(component.count { it > 0 } > component.count { it < 0 }){
            component.forEach { truthMap[it] = false; truthMap[-it] = true }
        }
        else {
            component.forEach { truthMap[it] = true; truthMap[-it] = false }
        }
    }
    givenSCC.forEach { component ->
        if(component.any { truthMap[it] == true }) {
            component.forEach { truthMap[it] = true}
        }
    }
    val ans = truthMap.filter { it.key > 0}.count { it.value }
    return ans.toString()
}