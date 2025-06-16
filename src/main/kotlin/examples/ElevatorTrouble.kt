package examples

import graphClasses.*

// https://open.kattis.com/problems/island
fun main() {
    val ans = elevatorTrouble(); _writer.flush()
    println(ans)
}


fun elevatorTrouble(): String {
    val (f,s,g,u,d) = readInts(5)
    val grid = Grid(1, f+1)
    grid.connectGrid{
        val upNode = grid.id2Node(it.y + u)
        val downNode = grid.id2Node(it.y - d)
        listOfNotNull(upNode, downNode)
    }
    val bfs = BFS(grid)
    bfs.bfsIterative(s,g)
    val dist = bfs.distances[g].toInt()
    return if(dist == Int.MAX_VALUE){
        "use the stairs"
    }else{
        dist.toString()
    }
}
