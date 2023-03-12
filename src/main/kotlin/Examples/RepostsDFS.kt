package Examples

import DFS
import Graph
import _writer
import readInt
import readStrings
import java.io.PrintWriter
import java.util.*
import kotlin.math.max


fun main() { _writer.solve(); _writer.flush() }
fun PrintWriter.solve() {
    val n = readInt()
    val g = Graph()
    repeat(n){
        val (v,e,u) = readStrings(3).map { it.lowercase(Locale.getDefault()) }
        g.addEdge(u,v)
    }
    val dfs = DFS(g.getAdjacencyList())
    var longestChain = 0
    repeat(n){
        dfs.dfsRecursive(it)
        longestChain = max(longestChain, dfs.depth)
    }
    println(longestChain)
}
