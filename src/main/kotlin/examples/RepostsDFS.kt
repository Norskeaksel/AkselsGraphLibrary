package examples
// https://codeforces.com/problemset/problem/522/A

import graphClasses.Graph
import graphClasses._writer
import graphClasses.readInt
import graphClasses.readStrings
import java.util.*
import kotlin.math.max


fun main() { print(repostsDFS()); _writer.flush() }
fun repostsDFS(): Int {
    val n = readInt()
    val g = Graph()
    repeat(n){
        val (v,_,u) = readStrings(3).map { it.lowercase(Locale.getDefault()) }
        g.addUnweightedEdge(u,v)
    }
    var longestChain = 0
    g.getNodes().forEach { node ->
        g.dfs(node)
        longestChain = max(longestChain, g.depth())
    }
    return longestChain
}
