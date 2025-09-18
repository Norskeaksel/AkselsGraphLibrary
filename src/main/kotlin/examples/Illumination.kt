package examples

import Clauses
import graphAlgorithms.twoSat
import graphClasses.*
import kotlin.math.abs

// https://open.kattis.com/problems/illumination
fun main() {
    val ans = illumination(); _writer.flush()
    println(ans)
}

fun illumination(): String {
    val (n, r, k) = readInts(3)
    val grid2 = Grid(n, n)
    val grid = Array(n) { BooleanArray(n) { false } }
    val lampTiles = mutableListOf<Tile>()
    repeat(k) {
        val (i, j) = readInts(2)
        val (x, y) = j - 1 to i - 1
        grid[x][y] = true
        grid2.addNode(Tile(x, y))
        lampTiles.add(Tile(x, y))
    }
    val clauses: Clauses = mutableListOf()
    grid2.nodes().let { lamps ->
        lamps.forEach { lamp1 ->
            lamps.forEach { lamp2 ->
                val xDistance = abs(lamp1.x - lamp2.x)
                val yDistance = abs(lamp1.y - lamp2.y)
                val id1 = lamp1.x + 1 + lamp1.y * n
                val id2 = lamp2.x + 1 + lamp2.y * n
                if (lamp1 != lamp2) {
                    if (yDistance <= r * 2 && xDistance == 0) {
                        clauses.add(id1 to id2)  // id1 V id2
                    }
                    if (xDistance <= r * 2 && yDistance == 0) {
                        clauses.add(-id1 to -id2)  // ¬id1 V ¬id2
                    }
                }
            }
        }
    }
    return if (twoSat(clauses) == null) "0" else "1"
}
