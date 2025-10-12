package examples
//https://codeforces.com/problemset/problem/20/C
import graphClasses.*
import readInts

fun main() {
    val path = dijkstraCF()
    path.forEach {
        print("$it ")
    }
}

fun dijkstraCF(): List<Int> {
    val (n, m) = readInts(2)
    val g = IntGraph(n+1)
    repeat(m) {
        val (u, v, w) = readInts(3)
        g.connect(u, v, w.toDouble())
    }
    g.dijkstra(1)
    val path = g.getPath(n)
    if (path.size == 1 && path[0] != 1) {
        return listOf(-1)
    }
    return path
}