package examples

import graphClasses.Graph
import graphGraphics.visualize
import readString
import java.lang.Math.pow


// https://open.kattis.com/problems/familydag
fun main() {
    val ans = familydag()
    println(ans)
}

fun familydag(): String {
    var graph = Graph(false)
    val ans = StringBuilder()
    while (true) {
        val line = readString() ?: break
        if (line == "done") {
            val sortedNodes = graph.nodes().sortedBy { it.toString() }
            val status = findHillbilliesAndParadoxes(graph, sortedNodes)
            sortedNodes.forEach { node ->
                if (status.containsKey(node)) {
                    ans.appendLine("$node ${status[node]}")
                }
            }
            ans.appendLine()
            //graph.visualize()
            graph = Graph(false)
            continue
        }
        val (v, _, u) = line.split(" ")
        graph.addEdge(u, v)
    }
    return ans.toString()
}

fun findHillbilliesAndParadoxes(graph: Graph, sortedNodes: List<Any>): MutableMap<Any, String> {
    val status = mutableMapOf<Any, String>()
    sortedNodes.forEach { node ->
        graph.bfs(node, node)
        if (graph.foundTarget()) {
            status[node] = "paradox"
            return@forEach
        }
        val visited = mutableSetOf<Any>()
        var isHillbilly = false
        fun hillbillyDfs(node: Any) {
            if (isHillbilly) return
            visited.add(node)
            graph.neighbours(node).forEach { v ->
                if (v in visited) isHillbilly = true
                hillbillyDfs(v)
            }
        }
        hillbillyDfs(node)
        if (isHillbilly) {
            status[node] = "hillbilly"
            return@forEach
        }
    }
    return status
}
