package graphClasses

import Components
import graphAlgorithms.twoSat2

class DependencyGraph : Graph() {
    private val dependencyGraph = Graph()

    init {
        dependencyGraph.addNode(0)
    }

    fun addClause(clause: DependencyGraph.() -> Unit) {
        this.clause()
    }

    private fun getUVIDPairs(node1: Any, node2: Any) = getOrAddNodeId(this) to getOrAddNodeId(node2)

    /** a V b <--> -a -> b and -b -> a */
    infix fun Any.or(other: Any) {
        val (u, v) = getUVIDPairs(this, other)
        dependencyGraph.addUnweightedEdge(-u, v)
        dependencyGraph.addUnweightedEdge(-v, u)
    }

    /** a ^ b <--> (a V b) and (-a V -b) */
    infix fun Any.xor(other: Any) {
        val (u, v) = getUVIDPairs(this, other)
        dependencyGraph.addUnweightedEdge(u, -v)
        dependencyGraph.addUnweightedEdge(v, -u)
        dependencyGraph.addUnweightedEdge(-u, v)
        dependencyGraph.addUnweightedEdge(-v, u)
    }

    /** not (a and b) <--> a -> -b and b -> -a */
    infix fun Any.nand(other: Any) {
        val (u, v) = getUVIDPairs(this, other)
        dependencyGraph.addUnweightedEdge(u, -v)
        dependencyGraph.addUnweightedEdge(v, -u)
    }

    infix fun Any.orNot(other: Any) {
        val (u, v) = getUVIDPairs(this, other)
        dependencyGraph.addUnweightedEdge(u, -v)
        dependencyGraph.addUnweightedEdge(-v, u)
    }

    override fun addNode(node: Any) {
        val id = getOrAddNodeId(node)
        dependencyGraph.addNode(id)
        dependencyGraph.addNode(-id)
    }

    override fun addWeightedEdge(node1: Any, node2: Any, weight: Double) {
        dependencyGraph.addWeightedEdge(node1, node2, weight)
    }

    override fun addUnweightedEdge(node1: Any, node2: Any) {
        dependencyGraph.addUnweightedEdge(node1, node2)
    }

    override fun nodes() = dependencyGraph.nodes().filter { it as Int > 0 }

    fun twoSat(
        initialTruthMap: Map<Any, Boolean> = mapOf(),
    ): Pair<Components, Map<Any, Boolean>>? {

        val (sCCs, integerTruthMap) =
            twoSat2(dependencyGraph) ?: return null
        val truthMap = integerTruthMap.filter { it.key > 0 }.mapKeys { k -> id2Node(k.key)!! }
        val sCCsZeroIndexed = sCCs.map { component -> component.filter { it > 0 }.map { it - 1 } }

        return sCCsZeroIndexed.map { component -> component.map { id2Node(it)!! } } to
                truthMapZeroIndexed.mapKeys { k -> id2Node(k.key)!!}
    }

}