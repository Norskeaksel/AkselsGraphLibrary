package graphMateKT.examples

import graphMateKT.graphClasses.Grid
import graphMateKT.graphClasses.Tile
import graphMateKT.readInts
import graphMateKT.readString

fun main() {
    val ans = grid()
    println(ans)
    System.out.flush()
}

/** Solves https://open.kattis.com/problems/grid */
fun grid(): Int {
    val (n, m) = readInts(2)
    val lines = mutableListOf<String>()
    repeat(n) {
        val line = readString()
        lines.add(line)
    }
    val grid = Grid(lines)
    grid.connectGrid { t ->
        val nr = t.data as Char - '0'
        val neighbours = listOfNotNull(
            grid.xy2Node(t.x + nr, t.y),
            grid.xy2Node(t.x - nr, t.y),
            grid.xy2Node(t.x, t.y + nr),
            grid.xy2Node(t.x, t.y - nr)
        )
        neighbours
    }
    val start = Tile(0, 0)
    val end = Tile(m - 1, n - 1)
    grid.bfs(start, end)
    val distance = grid.distanceTo(end).toInt()
    return if (distance == Int.MAX_VALUE) -1 else distance
}
