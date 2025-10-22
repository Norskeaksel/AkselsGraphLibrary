package examples

import graphClasses.Grid
import readInts
import readString

fun main() {
    val ans = amoebas()
    println(ans)
    System.out.flush()
}

/** Solves https://open.kattis.com/problems/amoebas */
fun amoebas(): String {
    val (m,_) = readInts(2)
    val lines = mutableListOf<String>()
    repeat(m){
        lines.add(readString())
    }
    val grid = Grid(lines)
    grid.deleteNodesWithData('.')
    grid.connectGrid { t->
        grid.getAllNeighbours(t)
    }
    return grid.stronglyConnectedComponents().size.toString()
}
