package examples

import Clauses
import graphAlgorithms.twoSat
import graphClasses.*

// https://open.kattis.com/problems/illumination
fun main() {
    val ans = illumination(); _writer.flush()
    println(ans)
}

fun illumination(): String {
    val (n, r, k) = readInts(3)
    // TODO, make into grid that connects lamp edges
    val grid = Array(n) { BooleanArray(n) { false } }
    val lampTiles = mutableListOf<Tile>()
    repeat(k) {
        val (i, j) = readInts(2)
        val (x, y) = j - 1 to i - 1
        grid[x][y] = true
        lampTiles.add(Tile(x, y))
    }
    val commonRowLamps = mutableMapOf<Tile, MutableList<Tile>>()
    val commonColumnLamps = mutableMapOf<Tile, MutableList<Tile>>()
    lampTiles.forEach { t ->
        for (x in t.x - r*2..t.x + r*2) {
            if (x in 0..<n && grid[x][t.y] && x != t.x) {
                commonRowLamps.getOrPut(t) { mutableListOf() }.add(Tile(x, t.y))
            }
        }
        for (y in t.y - r*2..t.y + r*2) {
            if (y in 0..<n && grid[t.x][y] && y != t.y) {
                commonColumnLamps.getOrPut(t) { mutableListOf() }.add(Tile(t.x, y))
            }
        }
    }
    val clauses: Clauses = mutableListOf()
    commonRowLamps.forEach { (lamp1, adjacentLamps) ->
        adjacentLamps.forEach { lamp2 ->
            val id1 = lamp1.x + 1 + lamp1.y * n
            val id2 = lamp2.x + 1 + lamp2.y * n
            clauses.add(id1 to -id2)
        }
    }
    debug(clauses)
    commonColumnLamps.forEach { (lamp1, adjacentLamps) ->
        adjacentLamps.forEach { lamp2 ->
            val id1 = lamp1.x + 1 + lamp1.y * n
            val id2 = lamp2.x + 1 + lamp2.y * n
            clauses.add(-id1 to id2)
        }
    }
    debug(clauses)
    return if (twoSat(clauses,false)) "1" else "0"
}
