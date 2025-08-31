package examples

import graphClasses.*

// https://open.kattis.com/problems/importspaghetti
fun main() {
    val ans = importspaghetti(); _writer.flush()
    println(ans)
}

fun importspaghetti(): String {
    val n = readInt()
    val g = Graph()
    val files = readString().split(" ")
    files.forEach { file ->
        g.addNode(file)
    }
    repeat(n) {
        val (file, importLines) = readString().split(" ")
        repeat(importLines.toInt()) {
            val imports = readString().replace("import ", "").split(", ")
            imports.forEach { g.addUnweightedEdge(file, it) }
        }
    }
    var shortestCycleLength = Int.MAX_VALUE
    var shortestCyclePath = listOf<Any>()

    g.getNodes().reversed().forEach { node ->
        g.bfs(node, node)
        if(g.foundTarget() && g.depth() < shortestCycleLength) {
            shortestCycleLength = g.depth()
            shortestCyclePath = g.getPath(node)
        }
    }
    if(shortestCycleLength == Int.MAX_VALUE) return "SHIP IT"
    return shortestCyclePath.joinToString().replace(", ", " ")
}

