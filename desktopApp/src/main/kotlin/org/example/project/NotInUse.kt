package org.example.project

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.decodeToImageBitmap
import java.io.File
import java.io.FileInputStream
import kotlin.math.pow

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

// Helper extension
private fun Float.pow(exponent: Int): Float = this.toDouble().pow(exponent.toDouble()).toFloat()

fun loadImageFromFile(path: String): ImageBitmap {
    return FileInputStream(File(path)).use { inputStream ->
        // loadImageBitmap is a top‑level extension function that reads from an InputStream
        inputStream.readAllBytes().decodeToImageBitmap()
    }
}

//Canvas(
//modifier = Modifier
//.weight(4f)
//.border(width = 5.dp, color = Color.Black)
//                    .pointerInput(Unit) {
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
//                    }