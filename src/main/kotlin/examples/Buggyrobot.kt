package examples

import graphClasses.*
import gridGraphics.visualizeSearch

// https://open.kattis.com/problems/buggyrobot
fun main() {
    val ans = buggyrobot(); _writer.flush()
    println(ans)
}

fun buggyrobot(): String {
    val (height, width) = readInts(2)
    val rows = mutableListOf<String>()
    var start: Int = 0
    var goal: Int = 0
    val walls = mutableListOf<Pair<Int,Int>>()
    repeat(height) { it ->
        rows.add((readString()).replace('R', 'S').replace('E', 'G'))
        if (rows.last().contains('S')) {
            start = rows.last().indexOf('S') + it * width
        }
        if (rows.last().contains('G')) {
            goal = rows.last().indexOf('G') + it * width
        }
        rows.last().forEachIndexed { index, c ->
            if (c == '#')
                walls.add(index to it)
        }
    }
    val grid = rows.joinToString("")
    val commands = readString()
    val size = width * height
    val goals = commands.indices.map { goal + it * size }.toSet()
    fun move(currentId: Int, direction: Char) =
        when (direction) {
            'L' -> if (currentId % width != 0) currentId - 1 else null
            'R' -> if ((currentId + 1) % width != 0) currentId + 1 else null
            'U' -> if (currentId - width >= 0) currentId - width else null
            'D' -> if (currentId + width < size) currentId + width else null
            else -> error("Invalid direction: $direction")
        }
    // BFS //TODO: fix mapping between grids.
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
    return goals.map { distances[it] }.min().toString()
}
