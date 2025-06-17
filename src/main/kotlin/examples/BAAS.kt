package examples

import graphClasses.*
import kotlin.math.min
import kotlin.system.measureTimeMillis

// https://open.kattis.com/problems/baas

fun main() {
    val ans = baas(); _writer.flush()
    println(ans)
}

fun baas(): Int {
    val n = readInt()
    val intGraph = IntGraph(n)
    val stepTime = readInts(n)
    repeat(n) { step_i ->
        val c_i = readInt()
        repeat(c_i) {
            val a_j = readInt() - 1
            intGraph.addEdge(step_i, a_j)
        }
    }
    val weightlessAdjacencyList = intGraph.getWeightlessAdjacencyList()
    var optimizedTime = Int.MAX_VALUE
    val time = measureTimeMillis {
        val topologicalOrder = DFS(intGraph).topologicalSort()
        val totalStepTime = IntArray(n)
        topologicalOrder.indices.forEach {
            topologicalOrder.forEachIndexed { i, node ->
                totalStepTime[node] = stepTime[node] + (weightlessAdjacencyList[node].maxOfOrNull { totalStepTime[it] } ?: 0)
                if (i == it)
                    totalStepTime[node] -= stepTime[node]
            }
            optimizedTime = min(optimizedTime, totalStepTime[n - 1])
        }
    }
    System.err.println("Time spent running: $time ms")
    return optimizedTime
}