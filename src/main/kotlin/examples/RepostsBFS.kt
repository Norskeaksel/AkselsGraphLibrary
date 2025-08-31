package examples
// https://codeforces.com/problemset/problem/522/A

import graphClasses.Graph
import graphClasses._writer
import graphClasses.readInt
import graphClasses.readStrings
import java.util.*
import kotlin.math.max


fun main() {
    print(RepostsBFS()); _writer.flush()
}

fun RepostsBFS(): Int {
    val n = readInt()
    val g = Graph()
    repeat(n) {
        val (v, _, u) = readStrings(3).map { it.lowercase(Locale.getDefault()) }
        g.addUnweightedEdge(u, v)
    }
    var longestChain = 0
    g.getNodes().forEach{ node ->
        g.bfs(node)
        longestChain = max(longestChain, g.depth() + 1)
    }
    return longestChain
}
