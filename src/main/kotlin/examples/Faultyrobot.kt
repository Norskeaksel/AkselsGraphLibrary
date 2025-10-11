package examples

import graphClasses.*
import readInts

// https://open.kattis.com/problems/faultyrobot
fun main() {
    val ans = faultyrobot()
    println(ans)
}

fun faultyrobot(): String {
    val (n, m) = readInts(2)
    val forcedGraph = IntGraph(n+1)
    val trueGraph = IntGraph(n+1)
    repeat(m) {
        val (a, b) = readInts(2)
        if (a < 0) {
            forcedGraph.addUnweightedEdge(-1 * a, b)
        } else {
            trueGraph.addUnweightedEdge(a, b)
        }
    }
    forcedGraph.dfs(1)
    val reachableNodes = forcedGraph.currentVisitedNodes()
    trueGraph.bfs(reachableNodes)
    val reachableWithBug = trueGraph.currentVisitedNodes().filter { trueGraph.distanceWeightedTo(it) <= 1.0 }
    forcedGraph.bfs(reachableWithBug)
    val trueReachableNodes = forcedGraph.currentVisitedNodes()
    val restNodes = trueReachableNodes.count { forcedGraph.neighbours(it).isEmpty() }
    return restNodes.toString()
}
