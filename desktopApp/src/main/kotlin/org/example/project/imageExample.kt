package org.example.project

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.decodeToImageBitmap
//import androidx.compose.ui.graphics.loadImageBitmap
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        // State for the loaded image and scale factor
        var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
        var scale by remember { mutableStateOf(1.0f) }

        // Load image from file (replace with your path)
        LaunchedEffect(Unit) {
//            imageBitmap = loadImageFromFile2("74ch-1-1.png")
            imageBitmap = useResource("74ch-1-1.png") { it.readAllBytes().decodeToImageBitmap() }
        }

        // UI: Slider + Canvas
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Control row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text("Scale: ${String.format("%.2f", scale)}", modifier = Modifier.width(80.dp))
                Slider(
                    value = scale,
                    onValueChange = { scale = it },
                    valueRange = 0.1f..3.0f,
                    modifier = Modifier.weight(1f)
                )
            }

            // Canvas that draws the image at scaled size
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                imageBitmap?.let { bitmap ->
                    // Use scale block to resize the image uniformly
                    scale(scale) {
                        // Draw the image at its original size (scaled by the block)
                        drawImage(bitmap)
                    }
                }
            }
        }
    }
}

suspend fun loadImageFromFile2(path: String): ImageBitmap {
    return withContext(Dispatchers.IO) {
        FileInputStream(File(path)).use { inputStream ->
            loadImageBitmap(inputStream)
        }
    }
}