package graphGraphics

import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer
import com.brunomnsilva.smartgraph.graph.DigraphEdgeList
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy
import graphClasses.Graph
import graphClasses.IntGraph
import javafx.animation.KeyFrame
import javafx.animation.PauseTransition
import javafx.animation.Timeline
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.stage.Stage
import javafx.util.Duration


class GraphGraphics : Application() {
    companion object {
        lateinit var graph: Graph
        lateinit var intGraph: IntGraph
        var screenTitle = "JavaFXGraph Visualization"
        var animationTicTimeOverride: Double? = null
        var startPaused = false
        var closeOnEnd = false
        var screenWidthOverride: Double? = null

        private val graphIsInitialized: Boolean
            get() = this::graph.isInitialized
    }


    private var isPaused = startPaused
    override fun start(stage: Stage) {
        val visitationOrder: List<Any>
        val path: List<Any>
        val graphVisualizer: DigraphEdgeList<Any, Any> = if (graphIsInitialized) {
            visitationOrder = graph.currentVisitedNodes()
            path = graph.finalPath()
            graph.convertToVisualizationGraph()
        } else {
            visitationOrder = intGraph.currentVisitedNodes()
            path = intGraph.finalPath()
            intGraph.convertToVisualizationGraph()
        }
        val animationKeyFrameTime =
            animationTicTimeOverride ?: (10_000.0 / graphVisualizer.numVertices())
        val initialPlacement: SmartPlacementStrategy = SmartCircularSortedPlacementStrategy()
        val graphView: SmartGraphPanel<Any, Any> = SmartGraphPanel(graphVisualizer, initialPlacement)
        graphView.setAutomaticLayout(true)
        val container = SmartGraphDemoContainer(graphView)
        val scene = Scene(container, 1024.0, 768.0)

        stage.title = screenTitle
        stage.scene = scene
        stage.show()
        graphView.init()

        val transitionTime = Duration.seconds(3.0)
        val pause = PauseTransition(transitionTime)
        pause.setOnFinished {
            graphView.setAutomaticLayout(false)
        }
        pause.play()
        val timeline = Timeline()
        visitationOrder.forEachIndexed { index, vertex ->
            val keyFrame = KeyFrame(Duration.millis(animationKeyFrameTime * index).add(transitionTime), {
                val stylableVertex = graphView.getStylableVertex(vertex)
                stylableVertex?.setStyleClass("visitedVertex")
            })
            timeline.keyFrames.add(keyFrame)
        }
        path.forEachIndexed { index, vertex ->
            val keyFrame = KeyFrame(
                Duration.millis(animationKeyFrameTime * index)
                    .add(Duration.millis(animationKeyFrameTime * visitationOrder.size)).add(transitionTime), {
                    val stylableVertex = graphView.getStylableVertex(vertex)
                    stylableVertex?.setStyleClass("pathVertex")
                })
            timeline.keyFrames.add(keyFrame)
        }

        timeline.play()

        scene.setOnKeyPressed { event ->
            if (event.code == KeyCode.SPACE) {
                toggleAnimation(timeline)
            }
        }

        scene.setOnMouseClicked {
            toggleAnimation(timeline)
        }
    }

    private fun toggleAnimation(timeline: Timeline) {
        if (isPaused) {
            timeline.play()
            isPaused = false
        } else {
            timeline.pause()
            isPaused = true
        }
    }
}

private fun Graph.convertToVisualizationGraph(): DigraphEdgeList<Any, Any> {
    val g = DigraphEdgeList<Any, Any>()
    nodes().forEach { node ->
        g.insertVertex(node)
    }
    nodes().forEach { node ->
        getEdges(node).forEach { edge ->
            val fromToWeight = Triple(node, edge.second, edge.first)
            g.insertEdge(node, edge.second, fromToWeight)
        }
    }
    return g
}

private fun IntGraph.convertToVisualizationGraph(): DigraphEdgeList<Any, Any> {
    val g = DigraphEdgeList<Any, Any>()
    nodes().forEach { node ->
        g.insertVertex(node)
    }
    nodes().forEach { node ->
        getEdges(node).forEach { edge ->
            val fromToWeight = Triple(node, edge.second, edge.first)
            g.insertEdge(node, edge.second, fromToWeight)
        }
    }
    return g
}