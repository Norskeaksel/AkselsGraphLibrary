package examples

import graphClasses.IntGraph
import graphGraphics.visualize
import readInts

// https://open.kattis.com/problems/brexit
fun main() {
    val ans = brexit()
    println(ans)
}

fun brexit(): String {
    val (c, p, x, l) = readInts(4)
    val g = IntGraph(c + 1, false)
    repeat(p) {
        val (u, v) = readInts(2)
        if (u == l)
            g.addEdge(l, v)
        else if (v == l)
            g.addEdge(l, u)
        else
            g.connect(u, v)
    }
    //g.visualize(bidirectional = true)
    return stayOrLeave(g, x, l)
}

fun stayOrLeave(g: IntGraph, x: Int, l: Int): String {
    val leavers = BooleanArray(g.size())
    leavers[x] = true
    val q = ArrayDeque<Int>()
    q.add(x)
    while (q.isNotEmpty()) {
        val c = q.removeFirst()
        val n = g.neighbours(c)
        n.forEach { u ->
            if (leavers[u])
                return@forEach
            val nn = g.neighbours(u)
            if (nn.count { v -> leavers[v] } >= nn.size / 2.0) {
                q.add(u)
                leavers[u] = true
            }
        }
    }
    return if (g.neighbours(l).count { v -> leavers[v] } < g.neighbours(l).size / 2.0) "leave" else "stay"
}

