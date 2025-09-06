package examples

import graphClasses.*
import gridGraphics.visualizeSearch

// https://open.kattis.com/problems/buggyrobot
fun main() {
    val ans = buggyrobot(); _writer.flush()
    println(ans)
}

data class TileData(val char: Char, val commandNr: Int)

fun buggyrobot(): String {
    var (height, width) = readInts(2)
    width++
    val rows = mutableListOf<String>()
    lateinit var start: Tile
    repeat(height) {
        rows.add((readString() + '#').replace('R', 'S').replace('E', 'G'))
        if (rows.last().contains('S')) {
            start = Tile(rows.last().indexOf('S'), it, TileData('S', 0))
        }
    }
    val commands = readString()
    val newWidth = width * commands.length + commands.length
    val grid = Grid(height = height, width = newWidth)
    repeat(height) { y ->
        repeat(newWidth) { x ->
            commands.indices.forEach { index ->
                val node = Tile(x, y, TileData(rows[y][x % (width)], index))
                grid.addNode(node)
            }
        }
    }
    repeat(commands.length) {
        grid.deleteNodesWithData(TileData('#', it))
    }
    grid.printChars()
    grid.connectGrid { t ->
        val neighbours = grid.getStraightNeighbours(t).filter { (it.data as TileData).char != '#' }
        val commandNr = t.x / width
        if (commandNr >= commands.length - 1)
            neighbours
        else {
            val nextGridTile = when (val command = commands[commandNr]) {
                'U' -> grid.xy2Node(t.x + width, t.y - 1)
                'D' -> grid.xy2Node(t.x + width, t.y + 1)
                'L' -> grid.xy2Node(t.x + width - 1, t.y)
                'R' -> grid.xy2Node(t.x + width + 1, t.y)
                else -> error("Invalid command: $command")
            }
            neighbours + listOfNotNull(nextGridTile)
        }
    }
    grid.bfs(start)
    //grid.visualizeSearch(screenTitle = "Searching with string: $commands")
    val goals = grid.currentVisitedNodes().filter { (it.data as TileData).char in listOf('E', 'G') }
    val distances = goals.map { grid.distanceTo(it) - it.x / width }
    return distances.min().toInt().toString()
}

/*private fun Tile.move(char: Char, grid: Grid): Tile = when (char) {
    'U' -> grid.xy2Node(x, y - 1) ?: this
    'D' -> grid.xy2Node(x, y + 1) ?: this
    'L' -> grid.xy2Node(x - 1, y) ?: this
    'R' -> grid.xy2Node(x + 1, y) ?: this
    else -> error("Invalid command: $char")
}*/

private fun Grid.printChars() {
    nodes.forEachIndexed { id, t ->
        if (id > 0 && id % width == 0)
            println()
        print(t?.data?.let { (it as TileData).char } ?: '#')
    }
    println()
}