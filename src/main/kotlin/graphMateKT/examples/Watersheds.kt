package graphMateKT.examples

import graphMateKT.graphClasses.Grid
import graphMateKT.graphClasses.Tile
import graphMateKT.readInt
import graphMateKT.readInts

fun main() {
    val ans = watersheds()
    println(ans)
    System.out.flush()
}

/** Solves https://open.kattis.com/problems/watersheds */
fun watersheds(): String {
    val n = readInt()
    var ans = ""
    repeat(n) { caseNr ->
        val (h, w) = readInts(2)
        val grid = Grid(height = h, width = w)
        repeat(h) { y ->
            repeat(w) { x ->
                val data = readInt()
                grid.addNode(Tile(x, y, data))
            }
        }
        grid.connectGrid(true) { t ->
            val drainage = grid.getStraightNeighbours(t).filter {
                (it.data as Int) < (t.data as Int)
            }
            if (drainage.isEmpty()) listOf() else
                listOf(drainage.minBy { it.data as Int })
        }
        val scc = grid.stronglyConnectedComponents()
        val sccSorted = scc.sortedBy { component -> component.minOf { it.x + w * it.y } }
        sccSorted.forEachIndexed { i, component ->
            component.forEach { t ->
                grid.addNode(Tile(t.x, t.y, 'a' + i))
            }
        }
        val sb = StringBuilder()
        sb.append("Case #${caseNr + 1}:\n")
        grid.nodes().forEachIndexed { id, t ->
            if (id > 0 && id % w == 0)
                sb.append("\n")
            sb.append(t.data.toString()).append(" ")
        }
        sb.append("\n")
        ans += sb.toString()
    }
    return ans.trimEnd()
}
