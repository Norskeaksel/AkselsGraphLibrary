package examples
// https://open.kattis.com/problems/bigtruck
import graphClasses.*
import readInt
import readInts
import readString
import kotlin.math.ceil
import kotlin.math.roundToInt

const val ITEM_BOOST = 1E-9

fun main() {
    val ans = bigtruck()
    println(ans)
}

fun bigtruck(): String {
    val n = readInt()
    val ig = IntGraph(n + 1)
    val items = listOf(0) + readString().split(" ").map { it.toInt() }
    val m = readInt()
    repeat(m) {
        val (a, b, d) = readInts(3)
        ig.addWeightedEdge(a, b, d - ITEM_BOOST * items[b])
        ig.addWeightedEdge(b, a, d - ITEM_BOOST * items[a])
    }
    ig.dijkstra(1)
    if (ig.weightedDistanceTo(n) == Double.POSITIVE_INFINITY) {
        return "impossible"
    }
    val nrOfItems = ((ceil(ig.weightedDistanceTo(n)) - ig.weightedDistanceTo(n)) / ITEM_BOOST).roundToInt() + items[1]
    return "${ig.weightedDistanceTo(n).roundToInt()} $nrOfItems"
}