package graphClasses

/** A Graph data structure supports noes of any datatype.
 *
 * Any new node is given an ID upon creation, which is used to build an adjacency list. The class maintains internal
 * maps between ID's and nodes and vice versa. Nodes can be connected unidirectional with `.addEdge(node1, node2)`
 * or bidirectional with `.connect(node1, node2)`.
 * Once the graph is built, you may use one of the following graph algorithms:
 *
  - **Breadth-First Search (BFS)**:
 *   - `bfs(startNode: T, target: T? = null, reset: Boolean = true)`
 *   - `bfs(startNodes: List<T>, target: T? = null, reset: Boolean = true)`
 *
 * - **Depth-First Search (DFS)**:
 *   - `dfs(startNode: T, reset: Boolean = true)`
 *
 * - **Dijkstra's Algorithm**:
 *   - `dijkstra(startNode: T, target: T? = null)`
 *
 * - **Floyd-Warshall Algorithm**:
 *   - `floydWarshall()`
 *
 * - **Topological Sort**:
 *   - `topologicalSort()`
 *
 * - **Strongly Connected Components (SCC)**:
 *   - `stronglyConnectedComponents()`
 *
 * - **Minimum Spanning Tree (MST)**:
 *   - `minimumSpanningTree()`
 *
 * @property isWeighted Indicates whether uses weighted or unweigtred edges. Traversel algorithms like BFS and DFS
 * operate on unweighted graphs, while minimum cost algorithms like Dijkstra, floyd warshall and prims is based on weighted edges */
class Graph(isWeighted:Boolean=true): BaseGraph<Any>(0, isWeighted) {
    private var nrOfNodes = 0
    private val node2id = mutableMapOf<Any, Int>()
    private val id2Node = mutableMapOf<Int, Any>()

    private fun getOrAddNodeId(node: Any): Int {
        return node2id[node] ?: addNode(node).run { node2id[node]!! }
    }

    override fun addNode(node: Any) {
        if (node2id.containsKey(node)) {
            //System.err.println("Warning: The node already exists, it can't be added again")
            return
        }
        nodes.add(node)
        node2id[node] = nrOfNodes
        id2Node[nrOfNodes++] = node
        adjacencyList.add(mutableListOf())
        unweightedAdjacencyList.add(mutableListOf())
    }

    override fun addWeightedEdge(node1: Any, node2: Any, weight: Double) {
        val id1 = getOrAddNodeId(node1)
        val id2 = getOrAddNodeId(node2)
        adjacencyList[id1].add(weight to id2)
    }

    override fun addUnweightedEdge(node1: Any, node2: Any) {
        val id1 = getOrAddNodeId(node1)
        val id2 = getOrAddNodeId(node2)
        unweightedAdjacencyList[id1].add(id2)
    }

    override fun node2Id(node: Any): Int? = node2id[node]


    override fun id2Node(id: Int): Any? = id2Node[id]


    override fun nodes(): List<Any> = id2Node.values.toList()
    override fun toString(): String {
        return buildString {
            adjacencyList.forEachIndexed { id, edges ->
                val edgeString = edges.joinToString { id2Node(it.second).toString() }
                append("${id2Node(id)} ----> [$edgeString]\n")
            }
        }
    }
}
