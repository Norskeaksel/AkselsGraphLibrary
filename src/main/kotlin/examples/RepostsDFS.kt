package examples
// https://codeforces.com/problemset/problem/522/A

import pathfindingAlgorithms.DFS
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
        g.addEdge(u,v)
    }
    val dfs = DFS(g.unweightedAdjacencyList)
    var longestChain = 0
    repeat(n){
        dfs.dfs(it)
        longestChain = max(longestChain, dfs.depth)
    }
    return longestChain
}
