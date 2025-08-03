package examples
// https://adventofcode.com/2023/day/3

import pathfindingAlgorithms.DFS
import graphClasses.Grid
import graphClasses.Tile

fun numbersConnectedToSymbol(input: List<String>): Int {
    val grid = Grid(input[0].length, input.size)
    initializeGrid(input, grid)
    val numbers = grid.getNodes().filter { it.data.toString().toIntOrNull() !in listOf(null, 0) }
    val partNumbers = mutableListOf<Int>()
    numbers.forEach { t ->
        val searcher = DFS(grid)
        searcher.dfs(grid.node2Id(t))
        if (searcher.depth > t.data.toString().length)
            partNumbers.add(t.data.toString().toInt())
    }
    println(partNumbers)
    return partNumbers.sum()
}

private fun initializeGrid(input: List<String>, grid: Grid, padWithDuplicates:Boolean = false) {
    repeat(2) {
        input.forEachIndexed { y, line ->
            val regex = Regex("\\d+|\\D+")
            val numbersAndSymbols = regex.findAll(line).map { it.value }.toList()
            var x = 0
            numbersAndSymbols.forEach { ns ->
                for (i in ns.indices) {
                    val value = when {
                        i == 0 && ns.toIntOrNull() != null -> ns
                        ns.toIntOrNull() != null -> if(padWithDuplicates) ns else "0"
                        else -> ns[i]
                    }
                    val t = Tile(x++, y, value)
                    if (it == 0)
                        grid.addNode(t)
                    else {
                        val neighbours =
                            grid.getAllNeighbours(t)
                                .filter { it.data != '.' && it.data.toString().toIntOrNull() in listOf(null, 0) }
                        neighbours.forEach { grid.addEdge(t, it) }
                    }
                }
            }
        }
    }
}

fun starWith2Numbers(input: List<String>): Long {
    val cleanedInput = mutableListOf<String>()
    input.forEach { line ->
        val newLine = line.map {
            if (it != '*' && !it.isDigit()) '.' else it
        }.joinToString("")
        cleanedInput.add(newLine)
    }
    val grid = Grid(input[0].length, input.size)
    initializeGrid(cleanedInput, grid, true)
    val gears = grid.getNodes().filter { it.data == '*' }
    val partNumbers = mutableListOf<Pair<Long, Long>>()
    gears.forEach { t ->
        val searcher = DFS(grid)
        searcher.dfs(grid.node2Id(t))
        val numbers = grid.getAllNeighbours(t).filter { it.data.toString().toIntOrNull() !in (listOf(null, 0)) }.map { it.data }.toSet()
        if (numbers.size == 2)
            partNumbers.add(Pair(numbers.first().toString().toLong(), (numbers.last().toString().toLong())))
    }
    println(partNumbers)
    return partNumbers.sumOf { it.first * it.second }
}

fun main() {
    val input = """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...${'$'}.*....
        .664.598..
    """.trimIndent().lines()
    require(input.isNotEmpty()) { "Input file must not be empty" }
    println(numbersConnectedToSymbol(input))
    println(starWith2Numbers(input))
}