package examples

import graphClasses.*

// https://open.kattis.com/problems/island
fun main() {
    val ans = islandBuses(); _writer.flush()
    println(ans)
}

fun getNrOfGroups(grid: Grid):Int{
    val groups = mutableListOf<List<Int>>()
    grid.getNodes().forEach { node ->
        grid.dfs(node)
        grid.dfs.getAndClearCurrentVisitedIds().let {
            if(it.isNotEmpty())
                groups.add(it)
        }
    }
    return groups.size
}

fun islandBuses(): String {
    val input = readLines()
    val maps = input.joinToString("\n").split("\n\n")
    val ans = mutableListOf<String>()
    maps.forEachIndexed {i, mapString ->
        val mapList = mapString.split("\n")
        val islandGrid = Grid(mapList).apply {
            markCharAsWall('.')
            markCharAsWall('B')
            connectGridDefaultWeightless()
        }
        val bridgesGrid = Grid(mapList).apply {
            markCharAsWall('.')
            markCharAsWall('X')
            markCharAsWall('#')
            connectGridDefaultWeightless()
        }
        val busesGrid = Grid(mapList).apply {
            markCharAsWall('.')
            connectGrid(true) {
                when (it.data) {
                    '#' -> getStraightNeighbours(it).filter { it.data != 'B' }
                    'B' -> getStraightNeighbours(it).filter { it.data != '#' }
                    else -> getStraightNeighbours(it)
                }
            }
        }
        val islands = getNrOfGroups(islandGrid)
        val bridges = getNrOfGroups(bridgesGrid)
        val buses = getNrOfGroups(busesGrid)
        ans.add("""
            Map ${i+1}
            islands: $islands
            bridges: $bridges
            buses needed: $buses
            
        """.trimIndent())
    }
    return ans.joinToString("\n")
}
