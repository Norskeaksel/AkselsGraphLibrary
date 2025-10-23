package graphMateKT.graphClasses

internal data class TrieNode(val children:MutableMap<Char, TrieNode> = mutableMapOf(), var isTerminal:Boolean = false)

internal class PrefixTree {
    val root = TrieNode()

    fun add(word:String){
        var current = root
        word.forEach { c ->
            if(c !in current.children)
                current.children[c] = TrieNode()
            current = current.children[c]!!
        }
        current.isTerminal = true
    }

    fun contains(word: String): Boolean{
        var current = root
        word.forEach { c ->
            if(c !in current.children)
                return false
            current = current.children[c]!!
        }
        return current.isTerminal
    }

    fun startsWith(prefix: String): Boolean{
        var current = root
        prefix.forEach { c ->
            if(c !in current.children)
                return false
            current = current.children[c]!!
        }
        return true
    }
}