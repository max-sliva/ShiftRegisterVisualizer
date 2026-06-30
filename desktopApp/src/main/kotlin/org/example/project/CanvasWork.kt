package org.example.project

//import org.jetbrains.compose.resources.decodeToImageBitmap

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.dp
import java.io.File
import java.io.FileInputStream
import kotlin.math.sqrt

// Helper extension
private fun Float.pow(exponent: Int): Float = Math.pow(this.toDouble(), exponent.toDouble()).toFloat()

//@Composable
//fun DraggableCanvas() {
//    // State for the rectangle's top-left corner
//    var position by remember { mutableStateOf(Offset(100f, 100f)) }
//
//    Canvas(
//        modifier = Modifier
//            .fillMaxSize()
//            .pointerInput(Unit) {
//                detectDragGestures { change, dragAmount ->
//                    // Update position by adding the drag delta
//                    position += dragAmount
//                    // Optionally, you can consume the event to prevent other gestures
//                    change.consume()
//                }
//            }
//    ) {
//        // Draw a rectangle at the current position
//        drawRect(
//            color = Color.Blue,
//            topLeft = position,
//            size = androidx.compose.ui.geometry.Size(80f, 60f)
//        )
//    }
//}

@Composable
fun CanvasWithButton() {
    var rectPosition by remember { mutableStateOf(Offset(100f, 100f)) }
    val rectSize = Size(120f, 80f)

    Box(modifier = Modifier.fillMaxSize()) {
        // Canvas as background
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(
                color = Color.Blue,
                topLeft = rectPosition,
                size = rectSize
            )
        }

        // Button overlay
        Button(
            onClick = {
                rectPosition = Offset(
                    x = (Math.random() * 400).toFloat(),
                    y = (Math.random() * 300).toFloat()
                )
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("Move Rectangle")
        }
    }
}

@Composable
fun ShiftWorkArea() {
//    val imageBitmap = remember { loadImageFromFile("74ch-1-1.png") }
    val imageBitmap = useResource("img1.png") { it.readAllBytes().decodeToImageBitmap() }
    var scale by remember { mutableStateOf(1f) }
    var initialSize: Size = Size.Zero
    var position by remember { mutableStateOf(Offset(0f, 0f)) }
    var isDragging by remember { mutableStateOf(false) }
    val rectSize = Size(imageBitmap.width.toFloat(), imageBitmap.height.toFloat())
    var imageSize by remember { mutableStateOf(rectSize) }
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
//                    detectDragGestures(
//                        onDragStart = { startOffset ->
//                            // Check if drag started inside the object (hit test)
//                            val rect = Rect(position, rectSize * scale)
//                            isDragging = rect.contains(startOffset)
//                           // imageSize = rect
//                        },
//                        onDrag = { change, dragAmount ->
//                            change.consume()
//                            if (isDragging) {
//                                position += dragAmount
//                            }
//                        },
//                        onDragEnd = {
//                            isDragging = false
////                            imageSize *= scale
//                        }
//                    )
                }
        ) {
            val canvasQuadrantSize = size / 4F //делим размер канваса на 2
            if (initialSize == Size.Zero) {
                initialSize = size
//            scale = initialSize.width / initialSize.width
            }
            scale = size.width / initialSize.width

            val canvasWidth = size.width
            val canvasHeight = size.height
            scale(scale = scale, pivot = Offset(x = 0f, y = 0f)) {
                println("initialSize = $initialSize, canvas size = $size")
                drawImage(
                    //и выводим его на канвас
                    image = imageBitmap,
//                topLeft = Offset(x = 0f, y = 0f), //координаты верхнего
                    topLeft = Offset(x = position.x, y = position.y), //координаты верхнего
                )
            }
//        drawControl(canvasQuadrantSize, imageBitmap)
        }
        Button(
            onClick = {
//                rectPosition = Offset(
//                    x = (Math.random() * 400).toFloat(),
//                    y = (Math.random() * 300).toFloat()
//                )
            },
            modifier = Modifier
//                .offset(x = (rectSize.width * scale).dp, y = ((rectSize.height * scale)/2).dp)
               // .padding(16.dp)
        ) {
            Text("Move Rectangle")
        }
    }
}

private fun DrawScope.drawControl(canvasQuadrantSize: Size, imageBitmap: ImageBitmap) {
    drawRect( //то рисуем его
        color = Color.Black, //цвет рисования
        size = canvasQuadrantSize //размер
    )
//или "/sdcard/face.png" на некоторых устройствах
    drawImage( //и выводим его на канвас
        image = imageBitmap,
        topLeft = Offset(x = 0f, y = 0f), //координаты верхнего

//        size = canvasQuadrantSize
    )
}

fun loadImageFromFile(path: String): ImageBitmap {
    return FileInputStream(File(path)).use { inputStream ->
        // loadImageBitmap is a top‑level extension function that reads from an InputStream
        inputStream.readAllBytes().decodeToImageBitmap()
    }
}