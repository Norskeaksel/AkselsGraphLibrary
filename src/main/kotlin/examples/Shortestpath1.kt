//https://open.kattis.com/problems/shortestpath1

import graphClasses.Dijkstra
import graphClasses.Graph
import graphClasses.readInt
import graphClasses.readInts

fun main(){
    while(true){
        val (n,m,q,s) = readInts(4)
        if(n+m+q+s == 0)
            break
        val g = Graph()
        repeat(n){
            g.addNode(it)
        }
        repeat(m){
            val (u,v,w) = readInts(3)
            g.addEdge(u,v,w.toDouble())
        }
        val distance = Dijkstra(g.getAdjacencyList()).run {
            this.dijkstra(s)
            this.distances
        }
        repeat(q){
            val goal = readInt()
            if(distance[goal] == Double.POSITIVE_INFINITY)
                println("Impossible")
            else
                println(distance[goal].toInt())
        }
    }
}