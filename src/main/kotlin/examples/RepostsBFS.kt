package examples
// https://codeforces.com/problemset/problem/522/A

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
    var longestChain = 0
    val nodes = g.nodes().map { it as String }
    repeat(n){
        g.bfs(nodes[it])
        longestChain = max(longestChain, g.maxDistance().toInt()+1)
    }
    return longestChain
}
