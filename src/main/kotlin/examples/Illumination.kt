package examples

import graphClasses.*
import readInts
import kotlin.math.abs

// https://open.kattis.com/problems/illumination
fun main() {
    val ans = illumination()
    println(ans)
}

fun illumination(): String {
    val (n, r, k) = readInts(3)
    val g = DependencyGraph()
    repeat(k) {
        val (i, j) = readInts(2)
        val (x, y) = j - 1 to i - 1
        g.addNode(Tile(x, y))
    }
    g.nodes().let { lamps ->
        lamps.forEach { lamp1 ->
            lamp1 as Tile
            lamps.forEach { lamp2 ->
                lamp2 as Tile
                val xDistance = abs(lamp1.x - lamp2.x)
                val yDistance = abs(lamp1.y - lamp2.y)
                if (lamp1 != lamp2) {
                    if (yDistance <= r * 2 && xDistance == 0) {
                        g.addClause { lamp1 V lamp2 }
                    }
                    if (xDistance <= r * 2 && yDistance == 0) {
                        g.addClause { !lamp1 V !lamp2 }
                    }
                }
            }
        }
    }
    return if (g.twoSat() == null) "0" else "1"
}
