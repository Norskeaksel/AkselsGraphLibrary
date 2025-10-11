package examples

import graphClasses.IntGraph
import readInt
import readInts

// https://open.kattis.com/problems/walkforest
fun main() {
    val ans = walkforest()
    println(ans)
}

fun walkforest(): String {
    val ans = StringBuilder()
    while (true) {
        val n = readInt()
        if (n == 0)
            break
        val g = IntGraph(n + 1)
        val m = readInt()
        repeat(m) {
            val (a, b, l) = readInts(3)
            g.connectWeighted(a, b, l)
        }
        g.dijkstra(2, 1)
        val dag = makeDAG(g)
        //g.visualize(true)
        // dag.visualize()
        ans.appendLine(nrOfPaths(dag))
    }
    return ans.toString()
}

private fun makeDAG(g: IntGraph): IntGraph {
    val dag = IntGraph(g.size())
    val q = ArrayDeque<Int>()
    q.add(1)
    val visited = BooleanArray(g.size())
    while (q.isNotEmpty()) {
        val u = q.removeFirst()
        if(visited[u]) continue
        visited[u] = true
        val uDist = g.distanceWeightedTo(u)
        g.weightedEdges(u).filter { (_, v) -> g.distanceWeightedTo(v) < uDist }.forEach { (w, v) ->
            if (visited[v]) return@forEach
            dag.addWeightedEdge(u, v, w)
            q.add(v)
        }
    }
    return dag
}

private fun nrOfPaths(g: IntGraph): Int {
    val nodesSorted = g.topologicalSort().reversed()
    val dp = IntArray(g.size())
    var c = 0
    var ans = 0
    nodesSorted.forEach { u ->
        val neighbours = g.neighbours(u)
        neighbours.forEach { v ->
            if (++c == 1)
                dp[u] = 1
            dp[v] += dp[u]
            ans = dp[v]
        }
    }
    return ans
}
