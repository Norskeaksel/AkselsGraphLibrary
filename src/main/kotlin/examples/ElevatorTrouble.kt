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
    fun connectBuilding(t: Tile): List<Tile>{
        val upNode = grid.id2Node(t.y + u)
        val downNode = grid.id2Node(t.y - d)
        return listOfNotNull(upNode, downNode)
    }
    grid.connectGrid(::connectBuilding)
    val bfs = BFS(grid)
    bfs.bfsIterative(s,g)
    val dist = bfs.distances[g].toInt()
    return if(dist == Int.MAX_VALUE){
        "use the stairs"
    }else{
        dist.toString()
    }
}
