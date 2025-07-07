package examples

import graphClasses.*
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.sqrt

// https://open.kattis.com/problems/honeyheist
fun main() {
    val ans = honeyheist(); _writer.flush()
    println(ans)
}

fun honeyheist(): String {
    val (r,n,a,b,x) = readInts(5)
    val nrOfCells = r.toDouble().pow(3) - (r - 1).toDouble().pow(3)
    val xy = ceil(sqrt(nrOfCells)).toInt()
    val grid = Grid(xy, xy)
    repeat(x){
        val id = readInt()
        grid.nodes[id] = null
    }
    return ""
}
