package examples

import graphClasses.*
import readInt
import readInts
import readString
import kotlin.math.ceil
import kotlin.math.roundToInt

private const val ITEM_BOOST = 1E-9

fun main() {
    val ans = bigtruck()
    println(ans)
    System.out.flush()
}

/** Solves https://open.kattis.com/problems/bigtruck */
fun bigtruck(): String {
    val n = readInt()
    val ig = IntGraph(n + 1)
    val items = listOf(0) + readString().split(" ").map { it.toInt() }
    val m = readInt()
    repeat(m) {
        val (a, b, d) = readInts(3)
        ig.addEdge(a, b, d - ITEM_BOOST * items[b])
        ig.addEdge(b, a, d - ITEM_BOOST * items[a])
    }
    ig.dijkstra(1)
    if (ig.distanceTo(n) == Double.POSITIVE_INFINITY) {
        return "impossible"
    }
    val nrOfItems = ((ceil(ig.distanceTo(n)) - ig.distanceTo(n)) / ITEM_BOOST).roundToInt() + items[1]
    return "${ig.distanceTo(n).roundToInt()} $nrOfItems"
}