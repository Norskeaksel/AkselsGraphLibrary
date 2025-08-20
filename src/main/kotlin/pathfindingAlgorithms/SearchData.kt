package pathfindingAlgorithms

data class SearchData(private val graphSize:Int){
    var visited: BooleanArray = BooleanArray(graph.size)
}
