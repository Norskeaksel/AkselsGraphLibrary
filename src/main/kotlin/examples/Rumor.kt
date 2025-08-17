package examples
//https://codeforces.com/problemset/problem/893/C

import graphClasses.Graph
import graphClasses.readInts

fun rumor(): Long {
    val g = Graph()
    val (n, m) = readInts(2)
    val c = readInts(n)
    repeat(n) {
        g.addNode(it + 1)
    }
    repeat(m) {
        val (x, y) = readInts(2)
        g.connect(x, y)
    }
    val components = g.stronglyConnectedComponents()
    System.err.println(components)
    var sum = 0L
    components.forEach { component ->
        val min = component.map { c[it] }.min()
        sum += min
    }
    return sum
}

fun main() {
    println(rumor())
}
