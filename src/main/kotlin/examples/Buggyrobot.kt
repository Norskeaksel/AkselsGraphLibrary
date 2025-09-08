package examples

import graphClasses.*
import kotlin.system.measureTimeMillis

// https://open.kattis.com/problems/buggyrobot
fun main() {
    val ans = buggyrobot(); _writer.flush()
    println(ans)
}


fun buggyrobot(): String {
    var (height, width) = readInts(2)
    width++
    val rows = mutableListOf<String>()
    lateinit var start: Tile
    val inputReadingTime = measureTimeMillis {
        repeat(height) {
            rows.add((readString() + '#').replace('R', 'S').replace('E', 'G'))
            if (rows.last().contains('S')) {
                start = Tile(rows.last().indexOf('S'), it, 'S')
            }
        }
    }

    // debug("Input reading took $inputReadingTime ms")
    val commands = readString()
    val layers = commands.length + 1
    val newWidth = width * layers
    //// debug("nedWidth = $newWidth")
    var grid: Grid
    val gridMakeingTime = measureTimeMillis {
        grid = Grid(height = height, width = newWidth)
        repeat(height) { y ->
            repeat(newWidth) { x ->
                val node = Tile(x, y, rows[y][x % width])
                grid.addNode(node)
            }
        }
    }
    grid.deleteNodesWithData('#')
    // debug("Grid making took $gridMakeingTime ms")
    val connectTime = measureTimeMillis {
        grid.connectGrid { t ->
            if (t.data == 'G') return@connectGrid emptyList<Tile>()
            val neighbours = grid.getStraightNeighbours(t)
            val commandNr = t.x / width
            if (commandNr >= commands.length)
                neighbours
            else {
                val nextGridTile = when (val command = commands[commandNr]) {
                    'U' -> grid.xy2Node(t.x + width, t.y - 1)
                    'D' -> grid.xy2Node(t.x + width, t.y + 1)
                    'L' -> grid.xy2Node(t.x + width - 1, t.y)
                    'R' -> grid.xy2Node(t.x + width + 1, t.y)
                    else -> error("Invalid command: $command")
                }
                neighbours + if (nextGridTile == null) listOfNotNull(grid.xy2Node(t.x + width, t.y)) else listOf(
                    nextGridTile
                )
            }
        }
    }
    // debug("Connecting took $connectTime ms")
    val bfsTime = measureTimeMillis {
        grid.bfs(start)
    }
    // debug("BFS took $bfsTime ms")
    //grid.printChars()
    //grid.visualizeSearch()
    var ans = ""
    val ansTime = measureTimeMillis {
        val goals = grid.currentVisitedNodes().filter { it.data == 'G' }
        val goal = goals.minBy { grid.unweightedDistanceTo(it) - it.x / width }
        val fewestChanges = grid.unweightedDistanceTo(goal) - goal.x / width
        ans = fewestChanges.toString()
    }
    // debug("Answer computing took $ansTime ms")
    return ans
}
