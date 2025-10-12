package examples

import graphClasses.Graph
import readInt
import readString

// https://open.kattis.com/problems/builddeps
fun main() {
    val ans = builddeps()
    println(ans)
}

fun builddeps(): String {
    val n = readInt()
    val g = Graph(false)
    repeat(n) {
        val words = readString().split(" ")
        val file = words[0].removeSuffix(":")
        val dependencies = if (words.size > 1) words.subList(1, words.size) else emptyList()
        dependencies.forEach { dependency ->
            g.addEdge(dependency, file)
        }
    }
    val changedFile = readString()
    g.addNode(changedFile)
    g.dfs(changedFile)
    val reachableNodes = g.currentVisitedNodes().toSet()
    val order = g.topologicalSort().reversed()
    val ans = StringBuilder()
    order.forEach {file ->
        if(file in reachableNodes)
            ans.appendLine(file)
    }
    // debug(affectedFiles)
    // g.visualize()
    return ans.toString()
}
