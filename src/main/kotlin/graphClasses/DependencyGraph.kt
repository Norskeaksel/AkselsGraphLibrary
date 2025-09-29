package graphClasses

import IntComponents
import graphAlgorithms.twoSat2

data class NotNode(val node: Any)
operator fun Any.not() = NotNode(this)

class DependencyGraph {
    private val nodes = mutableListOf<Any?>()
    // private var edgeNr = 1.0
    private var id = 1
    private val node2id = mutableMapOf<Any, Int>()
    private val id2Node = mutableMapOf<Int, Any>()
    val dependencyGraph = Graph()

    fun addClause(clause: DependencyGraph.() -> Unit) {
        this.clause()
    }
    fun addClause(node: Any) {
        val (u, _) = getUVIDPairs(node, node)
        dependencyGraph.addUnweightedEdge(-u, u)
    }
    fun addClause(node: NotNode) {
        val (u, _) = getUVIDPairs(node, node)
        dependencyGraph.addUnweightedEdge(u, -u)
    }

    private fun getUVIDPairs(node1: Any, node2: Any): Pair<Int, Int> {
        fun getNodeId(node: Any): Int {
            val id = node2id[if (node is NotNode) node.node else node] ?: error("Node $node not found")
            return if (node is NotNode) -id else id
        }
        val u = getNodeId(node1)
        val v = getNodeId(node2)
        return u to v
    }

    infix fun not(node: Any) = NotNode(node)

    /** a V b <--> -a -> b and -b -> a */
    infix fun Any.Or(other: Any) {
        val (u, v) = getUVIDPairs(this, other)
        dependencyGraph.addUnweightedEdge(-u, v)
        dependencyGraph.addUnweightedEdge(-v, u)
    }

    /** a ^ b <--> (a V b) and (-a V -b) */
    infix fun Any.Xor(other: Any) {
        this Or other
        val newThis = if(this is NotNode) this.node else this
        val newOther = if(other is NotNode) other.node else other
        !newThis Or !newOther
    }

    fun addNode(node: Any) {
        if (node2id.containsKey(node)) {
            System.err.println("Warning: The node already exists, it can't be added again")
            return
        }
        nodes.add(node)
        node2id[node] = id
        id2Node[id] = node
        dependencyGraph.addNode(id)
        dependencyGraph.addNode(-id)
        id++
    }

    fun nodes() = nodes.filterNotNull()

    fun twoSat(): Pair<IntComponents, Map<Any, Boolean>>? {
        val (intSCC, integerTruthMap) = twoSat2(dependencyGraph) ?: return null
        val truthMap = integerTruthMap.filter { it.key > 0 }.mapKeys { k -> id2Node[k.key]!! }
        return intSCC to truthMap
    }
}
