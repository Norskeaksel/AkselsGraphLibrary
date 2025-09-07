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
    val newWidth = width * commands.length + commands.length - 1
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
    grid.connectGrid { t ->
        val neighbours = grid.getStraightNeighbours(t).filter { (it.data as TileData).char != '#' }
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
            neighbours + if(nextGridTile==null) listOfNotNull(grid.xy2Node(t.x+width, t.y)) else listOf(nextGridTile)
        }
    }/*
    println("Correct connections:")
    grid.printWeightlessConnections()
    grid.connectGridByIndices { x, y ->
        val neighbours = grid.getStraightNeighbourIndices(x, y)
        val commandNr = x / width
        if (commandNr >= commands.length)
            neighbours
        else {
            val nextGridIndex = when (val command = commands[commandNr]) {
                'U' -> grid.xy2Index(x + width, y - 1)
                'D' -> grid.xy2Index(x + width, y + 1)
                'L' -> grid.xy2Index(x + width - 1, y)
                'R' -> grid.xy2Index(x + width + 1, y)
                else -> error("Invalid command: $command")
            }
            neighbours + if(nextGridIndex==null) listOfNotNull(grid.xy2Index(x, y)) else listOf(nextGridIndex)
        }
    }*/
    //println("Wrong connections:")
    //grid.printWeightlessConnections()
    grid.bfs(start)
    //grid.printChars()
    //grid.visualizeSearch(screenWidthOverride = 2000.0, animationTicTimeOverride = 200.0)
    val goals = grid.currentVisitedNodes().filter { (it.data as TileData).char == 'G' }
    val goal = goals.minBy { grid.distanceTo(it) - it.x / width }
    val fewestChanges = grid.distanceTo(goal) - goal.x / width
    return fewestChanges.toInt().toString()
}


/* BFS //TODO: fix mapping between grids.
val visited = BooleanArray(size * commands.length)
val currentVisited = mutableListOf<Int>()
val distances = IntArray(size * commands.length) { Int.MAX_VALUE / 2 }
val queue = ArrayDeque<Int>()
queue.add(start)
distances[start] = 0
while (queue.isNotEmpty()) {
    val currentId = queue.removeFirst()
    if (visited[currentId])
        continue
    visited[currentId] = true
    currentVisited.add(currentId)
    val currentDistance = distances[currentId]
    val commandNr = currentId / size
    val directions = listOf('L', 'R', 'U', 'D')
    directions.forEach { dir ->
        val newId = move(currentId % size, dir) ?: return@forEach
        if (dir == commands[commandNr] && currentId / size < commands.length) {
            if (grid[newId % size] == '.') {
                val shadowId = newId + size
                distances[shadowId] = currentDistance
                queue.add(shadowId)
            } else queue.add(currentId + size)
            return@forEach
        }
        if (grid[newId] == '#' || visited[newId])
            return@forEach
        else {
            distances[newId] = currentDistance + 1
            queue.add(newId)
        }
    }
}
debug(currentVisited)
val currentVisitedNodes = currentVisited.map { Tile( it % width , it / width) }
debug(currentVisitedNodes)
val newHeight = height * commands.length
Grid(width, newHeight).apply { walls.forEach { (x,y) ->
    commands.indices.forEach {
        deleteNodeAtXY(x,y + size * it) // TODO why does this fail?
    }
}}.visualizeSearch(
    currentVisitedNodes = currentVisitedNodes,
    nodeDistances = currentVisited.map { distances[it].toDouble() },
    screenTitle = "Seaching: $commands",
    closeOnEnd = true,
    screenWidthOverride = 6000.0
)
}*/

private fun Grid.printChars() {
    nodes.forEachIndexed { id, t ->
        if (id > 0 && id % width == 0)
            println()
        print(t?.data?.let { (it as TileData).char } ?: '#')
    }
    println()
}
