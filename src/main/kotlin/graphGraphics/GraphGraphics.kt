package graphGraphics

import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer
import com.brunomnsilva.smartgraph.graph.DigraphEdgeList
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy
import graphClasses.Graph
import javafx.animation.PauseTransition
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.util.Duration


class GraphGraphics:Application() {
    companion object {
        lateinit var g: DigraphEdgeList<Any,Any>
        var screenTitle = "JavaFXGraph Visualization"
        var animationTicTimeOverride: Double? = null
        var startPaused = false
        var closeOnEnd = false
        var screenWidthOverride: Double? = null
    }
    override fun start(stage: Stage) {
        val initialPlacement: SmartPlacementStrategy = SmartCircularSortedPlacementStrategy()
        val graphView: SmartGraphPanel<Any, Any> = SmartGraphPanel(g, initialPlacement)
        graphView.setAutomaticLayout(true)
        val container = SmartGraphDemoContainer(graphView)
        val scene = Scene(container, 1024.0, 768.0)

        stage.title = screenTitle
        stage.scene = scene
        stage.show()
        graphView.init()

        val transitionTime = Duration.seconds(1.5)
        val pause = PauseTransition(transitionTime)
        pause.setOnFinished {
            graphView.setAutomaticLayout(false)
        }
        pause.play()
    }
}

private fun Graph.convertToVisualizationGraph(): DigraphEdgeList<Any, Any> {
    val g = DigraphEdgeList<Any, Any>()
    getNodes().forEach { node ->
        g.insertVertex(node)
    }
    getNodes().forEach { node ->
        getEdges(node).forEach {edge ->
            g.insertEdge(node, edge.second, edge)
        }
    }
    return g
}

fun Graph.visualize() {
    GraphGraphics.g = convertToVisualizationGraph()
    Application.launch(GraphGraphics()::class.java)
}