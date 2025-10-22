package examples

import graphClasses.*
import readInt
import readInts
import kotlin.math.min

// Solves https://open.kattis.com/problems/baas
/* Note, this solution is close to the time limit. To make it pass, the submitted solution must cut away all library
   functions that's not needed. */

fun main() {
    val ans = baas()
    println(ans)
    System.out.flush()
}

fun baas(): Int {
    val n = readInt()
    val intGraph = IntGraph(n, false)
    val stepTime = readInts(n)
    repeat(n) { step_i ->
        val c_i = readInt()
        repeat(c_i) {
            val a_j = readInt() - 1
            intGraph.addEdge(step_i, a_j)
        }
    }
    var optimizedTime = Int.MAX_VALUE
    val topologicalOrder = intGraph.topologicalSort()
    val totalStepTime = IntArray(n)
    topologicalOrder.indices.forEach {
        topologicalOrder.forEachIndexed { i, node ->
            totalStepTime[node] = stepTime[node] + (intGraph.neighbours(node).maxOfOrNull { totalStepTime[it] } ?: 0)
            if (i == it)
                totalStepTime[node] -= stepTime[node]
        }
        optimizedTime = min(optimizedTime, totalStepTime[n - 1])
    }
    return optimizedTime
}