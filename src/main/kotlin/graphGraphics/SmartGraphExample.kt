package graphGraphics

import com.brunomnsilva.smartgraph.graph.Graph
import com.brunomnsilva.smartgraph.graph.GraphEdgeList
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy
import javafx.animation.KeyFrame
import javafx.animation.PauseTransition
import javafx.animation.Timeline
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.util.Duration

// On linux, set -Dsun.java2d.opengl=True in intellij VM options for better performance
class SmartGraphExample : Application() {
    override fun start(stage: Stage) {
        val g: Graph<String, String> = GraphEdgeList()
        val initialPlacement: SmartPlacementStrategy = SmartCircularSortedPlacementStrategy()
        val graphView: SmartGraphPanel<String, String> = SmartGraphPanel(g, initialPlacement)
        graphView.setAutomaticLayout(true)
        val scene = Scene(graphView, 1024.0, 768.0)

        stage.title = "JavaFXGraph Visualization"
        stage.scene = scene
        stage.show()

        // IMPORTANT! - Called after scene is displayed, so we can initialize the graph visualization
        graphView.init()

        g.insertVertex("A")
        g.insertVertex("B")
        g.insertVertex("C")
        g.insertVertex("D")
        g.insertVertex("E")
        g.insertVertex("F")
        g.insertVertex("G")

        g.insertEdge("A", "B", "1")
        g.insertEdge("A", "C", "2")
        g.insertEdge("A", "D", "3")
        g.insertEdge("A", "E", "4")
        g.insertEdge("A", "F", "5")
        g.insertEdge("A", "G", "6")

        g.insertVertex("H")
        g.insertVertex("I")
        g.insertVertex("J")
        g.insertVertex("K")
        g.insertVertex("L")
        g.insertVertex("M")
        g.insertVertex("N")

        g.insertEdge("H", "I", "7")
        g.insertEdge("H", "J", "8")
        g.insertEdge("H", "K", "9")
        g.insertEdge("H", "L", "10")
        g.insertEdge("H", "M", "11")
        g.insertEdge("H", "N", "12")
        g.insertEdge("A", "H", "0")
        graphView.update()

        // Pause then stop the automatic layout
        val pauseTime = Duration.seconds(2.5)
        val pause = PauseTransition(pauseTime)
        pause.setOnFinished {
            graphView.setAutomaticLayout(false)
        }
        pause.play()

        val timeline = Timeline()
        g.vertices().forEachIndexed { index, vertex ->
            val keyFrame = KeyFrame(Duration.millis(250.0 * index).add(pauseTime), {
                val stylableVertex = graphView.getStylableVertex(vertex)
                stylableVertex?.setStyleClass("myVertex")
            })
            timeline.keyFrames.add(keyFrame)
        }
        timeline.play()
    }
}

fun main() {
    Application.launch(SmartGraphExample::class.java)
}