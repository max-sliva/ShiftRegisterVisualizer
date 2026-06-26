package org.example.project

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.useResource
//import org.jetbrains.compose.resources.decodeToImageBitmap
import org.jetbrains.skia.Image
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

@Composable
fun ShiftWorkArea() {
//    val imageBitmap = remember { loadImageFromFile("74ch-1-1.png") }
    val imageBitmap = useResource("img1.png") { it.readAllBytes().decodeToImageBitmap() }
    var scale by remember { mutableStateOf(1.0f) } //todo связать с размерами окна
    Canvas(modifier = Modifier.fillMaxSize()){
        val canvasQuadrantSize = size / 4F //делим размер канваса на 2
        val canvasWidth = size.width
        val canvasHeight = size.height
//        drawRect( //то рисуем его
//            color = Color.Black, //цвет рисования
//            size = canvasQuadrantSize //размер
//        )
        scale(scale) {
            drawImage(
                //и выводим его на канвас
                image = imageBitmap,
                topLeft = Offset(x = 0f, y = 0f), //координаты верхнего
            )
        }
//        drawControl(canvasQuadrantSize, imageBitmap)
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
//    drawCircle( // рисуем внешний круг
//        Color.Gray, //цвет рисования
//        radius = outerCircleRadius, //и радиус
////        center = Offset(circleX, circleY)
//    )
//    drawCircle( // рисуем внутренний круг
//        Color.Blue, //цвет рисования
//        radius = innerCircleRadius, //и радиус
//        center = Offset(circleX, circleY)
//    )
}

fun loadImageFromFile(path: String): ImageBitmap {
    return FileInputStream(File(path)).use { inputStream ->
        // loadImageBitmap is a top‑level extension function that reads from an InputStream
        inputStream.readAllBytes().decodeToImageBitmap()
    }
}