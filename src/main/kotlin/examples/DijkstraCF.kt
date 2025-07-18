package examples
//https://codeforces.com/problemset/problem/20/C
import graphClasses.*


fun main() {
    val path = dijkstraCF(); _writer.flush()
    path.forEach {
        print("$it ")
    }
}

fun dijkstraCF(): List<Int> {
    val (n, m) = readInts(2)
    val g = IntGraph()
    repeat(n + 1) {
        g.addNode(it)
    }
    repeat(m) {
        val (u, v, w) = readInts(3)
        g.connect(u, v, w.toDouble())
    }
    val dijkstra = Dijkstra(g.getAdjacencyList())
    dijkstra.dijkstra(1)
    val path = getPath(n, dijkstra.parents)
    if (path.size == 1 && path[0] != 1) {
        return listOf(-1)
    }
    return path
}