package org.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.VerticalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState

import java.awt.Cursor

private fun Modifier.cursorForHorizontalResize(): Modifier =
    pointerHoverIcon(PointerIcon(Cursor(Cursor.E_RESIZE_CURSOR)))

@Composable
fun MainWindow() {
  println("Hello world!")
}

@Composable
fun ResizableSplitWindow() {
    val splitterState = rememberSplitPaneState()
    splitterState.moveEnabled = true
    splitterState.positionPercentage = 0.8F
   HorizontalSplitPane (
        splitPaneState = splitterState,
        modifier = Modifier
            .fillMaxSize()
            .border(5.dp, Color.Gray)
    ) {
        first(150.dp) {
            Column(Modifier.fillMaxSize()) {
                Text("Left Panel", modifier = Modifier.wrapContentSize())
                ShiftWorkArea()
            }
        }

        second(120.dp) {
            Column(Modifier.fillMaxSize()) {
                Text("Right Panel", modifier = Modifier.weight(1f))
            }
        }
        splitter {
            visiblePart {
                Box(
                    Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .background(MaterialTheme.colors.background)
                )
            }
            handle {
                Box(
                    Modifier
                        .markAsHandle()
                        .cursorForHorizontalResize()
                        .background(SolidColor(Color.Gray), alpha = 0.50f)
                        .width(9.dp)
                        .fillMaxHeight()
                )
            }
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ShiftRegisterVisualizer",
    ) {
//        App()
//        MainWindow()// { /*println("Closed") */}
        ResizableSplitWindow()
    }
}