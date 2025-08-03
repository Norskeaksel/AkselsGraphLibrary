package examples
// https://codeforces.com/problemset/problem/522/A

import pathfindingAlgorithms.BFS
import graphClasses.Graph
import graphClasses._writer
import graphClasses.readInt
import graphClasses.readStrings
import java.util.*
import kotlin.math.max


fun main() { print(RepostsBFS()); _writer.flush() }
fun RepostsBFS(): Int {
    val n = readInt()
    val g = Graph()
    repeat(n){
        val (v,_,u) = readStrings(3).map { it.lowercase(Locale.getDefault()) }
        g.addEdge(u,v)
    }
    val bfs = BFS(g)
    var longestChain = 0
    repeat(n){
        bfs.bfs(listOf(it))
        longestChain = max(longestChain, bfs.distances.maxOrNull()!!.toInt()+1)
    }
    return longestChain
}
