package examples

import graphClasses.*
import gridGraphics.visualizeSearch
import kotlin.collections.ArrayDeque

// https://open.kattis.com/problems/twoknights
fun main() {
    val ans = twoknights(); _writer.flush()
    println(ans)
}

fun twoknights(): String {
    val lowercaseKeyboard = """qwertyuiop
asdfghjkl;
zxcvbnm,./
!!      !!"""
    val uppercaseKeyboard = """QWERTYUIOP
ASDFGHJKL:
ZXCVBNM<>?
!!      !!"""
    val width = 10
    val height = 4
    val grid = Grid(width, height)
    val combinedKeyboard = lowercaseKeyboard.zip(uppercaseKeyboard).filter { it != '\n' to '\n' }
    combinedKeyboard.forEachIndexed { index, pair ->
        val x = index % width
        val y = index / width
        grid.addNode(Tile(x, y, pair))
    }
    grid.connectGrid { t ->
        val (x, y) = t.x to t.y
        val possibleMoves = listOf(
            (x + 2 to y + 1), (x + 2 to y - 1), (x - 2 to y + 1), (x - 2 to y - 1),
            (x + 1 to y + 2), (x + 1 to y - 2), (x - 1 to y + 2), (x - 1 to y - 2)
        )
        possibleMoves.mapNotNull { (xx, yy) -> grid.xy2Node(xx, yy) }
    }
    var ans = ""
    while (true) {
        val target = readString()
        if (target == "*")
            break
        ans += grid.stateSearch(target) + "\n"
    }
    return ans
}

data class State(val knight1: Tile, val knight2: Tile, val currentPoemIndex: Int)

private fun Grid.stateSearch(target: String): String {
    val initialKnight1 = Tile(0, height - 1, '!' to '!')
    val initialKnight2 = Tile(width - 1, height - 1, '!' to '!')
    val initialState = State(initialKnight1, initialKnight2, 0)
    val stateQueue = ArrayDeque<State>()
    stateQueue.add(initialState)
    val visitedStates = mutableSetOf<State>()
    while (stateQueue.isNotEmpty()) {
        val currentState = stateQueue.removeFirst()
        if(currentState in visitedStates) continue
        visitedStates.add(currentState)
        val (currentKnight1, currentKnight2, currentPoemIndex) = currentState
        if (currentPoemIndex == target.length) {
            return "1"
        }
        val nextChar = target[currentPoemIndex]
        val newStates = mutableListOf<State>()
        listOf(currentKnight1 to currentKnight2, currentKnight2 to currentKnight1)
            .forEach { (movingKnight, standingKnight) ->
                newStates.addAll(possibleKnightMoves(movingKnight, standingKnight, nextChar).map {
                    val newPoemIndex = if (it.data == ('!' to '!')) currentPoemIndex else currentPoemIndex + 1
                    if (movingKnight == currentKnight1)
                        State(it, standingKnight, newPoemIndex)
                    else
                        State(standingKnight, it, newPoemIndex)
                })
            }
        val nextLetterStates = newStates.filter { it.currentPoemIndex > currentPoemIndex }
        if (nextLetterStates.isNotEmpty()) {
            stateQueue.addAll(nextLetterStates)
        } else
            stateQueue.addAll(newStates)
    }
    return "0"
}


private fun Grid.possibleKnightMoves(moveingKnight: Tile, standingKnight: Tile, nextChar: Char): List<Tile> {
    val shiftIsPressedByStandingKnight = (standingKnight.data as Pair<*, *>).first == '!'
    val possibleKnightMoves = getNeighbours(moveingKnight).filter {
        val tileData = it.data as Pair<*, *>
        tileData.first == '!' ||
                if (shiftIsPressedByStandingKnight) {
                    tileData.second == nextChar
                } else {
                    tileData.first == nextChar
                }
    }
    return possibleKnightMoves
}
