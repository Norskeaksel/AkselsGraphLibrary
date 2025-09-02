package examples

import graphClasses.*
import gridGraphics.visualizeSearch

// https://open.kattis.com/problems/twoknights
fun main() {
    val ans = twoknights(); _writer.flush()
    println(ans)
}

fun twoknights(): String {
    val lowercaseKeyboard = """qwertyuiop
asdfghjkl;
zxcvbnm,./
!!######!!"""
    val uppercaseKeyboard = """QWERTYUIOP
ASDFGHJKL:
ZXCVBNM<>?
!!######!!"""
    val width = 10
    val height = 4
    val grid = Grid(width, height)
    val combinedKeyboard = lowercaseKeyboard.zip(uppercaseKeyboard)
    grid.connectGrid {t ->
        val (x,y) = t.x to t.y
        val possibleMoves = listOf(
            (x+2 to y+1), (x+2 to y-1), (x-2 to y+1), (x-2 to y-1),
            (x+1 to y+2), (x+1 to y-2), (x-1 to y+2), (x-1 to y-2)
        )
        possibleMoves.mapNotNull { (xx, yy) -> grid.xy2Node(xx, yy) }
    }
    val possibleMoves = TODO()

    val input = readString()
    repeat(input.length) { i ->
        val target = input[i]


    }
    debug(input)
    // grid.visualizeSearch()
    return ""
}

data class Move(val knight1:Boolean, val from: Pair<Int,Int>, val to: Pair<Int,Int>) {
}