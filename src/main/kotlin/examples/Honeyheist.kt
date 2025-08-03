package examples

import graphClasses.*
import pathfindingAlgorithms.BFS
import pathfindingAlgorithms.getPath
import java.lang.Math.pow

// https://open.kattis.com/problems/honeyheist
fun main() {
    val ans = honeyheist(); _writer.flush()
    println(ans)
}

fun honeyheist(): String {
    val (r, n, a, b, w) = readInts(5)
    val xy = 2 * r - 1
    val grid = Grid(xy, xy)
    var id = 1
    repeat(r) { y ->
        for (x in 0 until r + y) {
            grid.addNode(Tile(x, y, id++))
        }
    }
    for (i in 1 until r) {
        for (x in 0 until xy - i) {
            grid.addNode(Tile(x + i, (r - 1 + i), id++))
        }
    }
    debug("id == $id == $r^3 - ($r-1)^3: ${id.toDouble() == pow(r.toDouble(), 3.0) - pow(r.toDouble() - 1, 3.0)}")
    grid.print()
    repeat(w) {
        val wallId = readInt()
        grid.deleteNodeWithData(wallId)
    }

    grid.print()
    grid.connectGrid { t ->
        val downLeft = grid.xy2Node(t.x - 1, t.y + 1)
        val upRight = grid.xy2Node(t.x + 1, t.y - 1)
        grid.getAllNeighbours(t).filter { it != downLeft && it != upRight && it.data!=null }
    }
    // println("14 edges: ${grid.getEdges(grid.getNodes().first { it.data == 14 }).map { grid.id2Node(it.second)!!.data }}")
    val bfs = BFS(grid)
    val startId = grid.node2Id(grid.getNodes().first{ it.data == a })
    val endId = grid.node2Id(grid.getNodes().first{ it.data == b })
    bfs.bfs(startId)
    val distance = bfs.distances[endId]
    val path = getPath(endId, bfs.parents)
    println(path.map { grid.id2Node(it)!!.data })
    return if (distance <= n) {
        distance.toString()
    } else {
        "No"
    }
}
