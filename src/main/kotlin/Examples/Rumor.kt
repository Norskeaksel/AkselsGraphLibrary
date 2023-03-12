package Examples

import DFS
import Graph

//https://codeforces.com/problemset/problem/893/C


fun readString() = readLine()!!
fun readStrings() = readString().split(" ")
fun readInts() = readStrings().map { it.toInt() }

fun main() {
    val g = Graph()
    val (n, m) = readInts()
    val c = readInts()
    repeat(n) {
        g.addNode(it + 1)
    }
    repeat(m) {
        val (x, y) = readInts()
        g.connect(x, y)
    }
    val dfs = DFS(g.getAdjacencyList())
    val components = mutableListOf<List<Int>>()
    for(i in 0 until g.size()) {
        if(dfs.visited[i]) continue
        dfs.dfsRecursive(i)
        val component = dfs.getCurrentVisited()
        if(component.isNotEmpty())
            components.add(component)
    }

    System.err.println(components)
    var sum = 0L
    components.forEach { component ->
        val min = component.map { c[it] }.min()
        sum += min
    }
    println(sum)
}
