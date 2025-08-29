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
            imports.forEach { g.addEdge(file, it, 1.0) }
        }
    }
    val r = g.floydWarshall()
    r.printDistances()
    return ""
}

