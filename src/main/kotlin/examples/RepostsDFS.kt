package examples

import graphClasses.Graph
import readInt
import readStrings
import java.util.*
import kotlin.math.max

fun main() {
    print(repostsDFS())
}

/** Solves https://codeforces.com/problemset/problem/522/A */
fun repostsDFS(): Int {
    val n = readInt()
    val g = Graph()
    repeat(n) {
        val (v, _, u) = readStrings(3).map { it.lowercase(Locale.getDefault()) }
        g.addEdge(u, v, 1.0)
    }
    var longestChain = 0
    g.nodes().forEach { node ->
        g.dfs(node)
        longestChain = max(longestChain, g.depth())
    }
    return longestChain
}
