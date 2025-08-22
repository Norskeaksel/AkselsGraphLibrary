package org.gridgraphics

import graphClasses.Grid
import javafx.animation.KeyFrame
import javafx.animation.PauseTransition
import javafx.animation.Timeline
import javafx.application.Application
import javafx.event.ActionEvent
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.util.Duration


class FXGraphics : Application() {
    companion object {
        var grid = Grid(0, 0)
        var visitedNodes = listOf<Int>()
        var nodeDistances = listOf<Double>()
        var finalPath = listOf<Int>()
        var animationTimeOverride: Double? = null
        var closeOnEnd = false
        var sceneWithOverride: Double? = null
        var windowTitle = "Grid visualizer (Click or space to pause and resume)"
        var startPaused = false
    }

    var animationKeyFrameTime = Duration.millis(animationTimeOverride ?: (10_000.0 / visitedNodes.size))
    val sceneWith = sceneWithOverride ?: 1000.0
    val sceneHeight = 1000.0
    val canvas = Canvas(sceneWith, sceneHeight)
    val gc = canvas.graphicsContext2D
    val xNodes = sceneWith / (grid.width)
    val yNodes = sceneHeight / grid.height
    val minEdgeLength = xNodes.coerceAtMost(yNodes)

    override fun start(primaryStage: Stage) {
        primaryStage.title = windowTitle
        val root = Group()
        gc.fill = Color.BLACK
        grid.getNodes().forEach { node ->
            drawSquare(node.x, node.y, Color.BLACK)
        }
        root.children.add(canvas)
        primaryStage.scene = Scene(root)
        primaryStage.show()
        animateVisitedNodes(primaryStage)
    }

    private fun drawSquare(x: Int, y: Int, color: Color) {
        gc.fill = color
        gc.fillRect(x * minEdgeLength, y * minEdgeLength, minEdgeLength - 1, minEdgeLength - 1)
    }

    private var isPaused = startPaused
    private fun animateVisitedNodes(stage: Stage) {
        val scene = stage.scene
        val timeline = Timeline()
        println("animationKeyFrameTime: $animationKeyFrameTime")
        val maxDepth = nodeDistances.maxOrNull() ?: 1.0
        visitedNodes.forEachIndexed { i, nodeId ->
            val color = getInterpolatedColor((nodeDistances.getOrNull(i) ?: 0).toDouble(), maxDepth)
            val keyFrame = KeyFrame(
                animationKeyFrameTime.multiply(i.toDouble()), squareDrawer(
                    nodeId, color
                )
            )
            timeline.keyFrames.add(keyFrame)
        }
        finalPath.forEachIndexed { i, nodeId ->
            val keyFrame = KeyFrame(
                animationKeyFrameTime.multiply(1.05 * (i.toDouble() + visitedNodes.size)), squareDrawer(
                    nodeId, Color.GREEN
                )
            )
            timeline.keyFrames.add(keyFrame)
        }
        timeline.setOnFinished {
            if (closeOnEnd) {
                stage.close()
            }
        }
        val pause = PauseTransition(Duration.seconds(.5))
        pause.setOnFinished { timeline.play() }
        if (!startPaused)
            pause.play()

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

    private fun squareDrawer(nodeId: Int, color: Color): (ActionEvent) -> Unit {
        val node = grid.id2Node(nodeId)!!
        return { drawSquare(node.x, node.y, color) }
    }


    private fun getInterpolatedColor(value: Double, max: Double, min: Double = 0.0): Color {
        if (value !in min..max) {
            error("Value $value is not in range $min..$max")
        }
        val normalized =
            (value - min) / (max - min) - 1e-6 // -1e-6 to avoid 1.0 and thus trying to interpolate out of bounds
        val colors = arrayOf(
            Color.RED,
            Color.ORANGE,
            Color.YELLOW,
            Color.LIGHTSKYBLUE,
            Color.BLUE,
        )
        val index = (normalized * (colors.size - 1)).toInt()
        return colors[index].interpolate(colors[index + 1], (normalized * (colors.size - 1)) % 1)
    }

}
