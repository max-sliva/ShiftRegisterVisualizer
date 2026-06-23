package org.example.project

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

@Composable
fun ShiftWorkArea() {
    Canvas(modifier = Modifier.fillMaxSize()){
        val canvasQuadrantSize = size / 4F //делим размер канваса на 2
        val canvasWidth = size.width
        val canvasHeight = size.height
        drawRect( //то рисуем его
            color = Color.Black, //цвет рисования
            size = canvasQuadrantSize //размер
        )
        drawControl()
    }
}

private fun DrawScope.drawControl(//для рисования сдвигового регистра
//    outerCircleRadius: Float,
//    innerCircleRadius: Float,
//    circleX: Float,
//    circleY: Float
) {

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