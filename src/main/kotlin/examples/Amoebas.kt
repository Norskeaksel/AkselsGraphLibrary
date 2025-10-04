package examples

import graphClasses.Grid
import readInts
import readString

// https://open.kattis.com/problems/amoebas
fun main() {
    val ans = amoebas()
    println(ans)
}

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
