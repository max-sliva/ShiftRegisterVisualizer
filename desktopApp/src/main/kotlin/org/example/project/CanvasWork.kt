package org.example.project

//import org.jetbrains.compose.resources.decodeToImageBitmap

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.imageResource
//import androidx.compose.ui.unit.dp
import java.io.File
import java.io.FileInputStream
import shiftregistervisualizer.desktopapp.generated.resources.Res
import shiftregistervisualizer.desktopapp.generated.resources.withBreadBoard
import kotlin.math.pow

// Helper extension
private fun Float.pow(exponent: Int): Float = this.toDouble().pow(exponent.toDouble()).toFloat()

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
fun ShiftWorkArea() {
//    val imageBitmap = remember { loadImageFromFile("74ch-1-1.png") }
//    val imageBitmap = useResource("img1.png") { it.readAllBytes().decodeToImageBitmap() }
//    val imageBitmap = useResource("withBreadBoard.PNG") { it.readAllBytes().decodeToImageBitmap() }

    var imageBitmap = imageResource(Res.drawable.withBreadBoard)

    var scale = remember { mutableStateOf(1f) }
//    var scale2 = scale.value
    val rectSize = Size(imageBitmap.width.toFloat(), imageBitmap.height.toFloat())
    var initialSize: Size = Size.Zero
    var position by remember { mutableStateOf(Offset(0f, 0f)) }
    var isDragging by remember { mutableStateOf(false) }
    var rowHeightDp by remember { mutableStateOf(0) }
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 20.dp)
            .border(width = 5.dp, color = Color.Black)
            .onGloballyPositioned { coordinates ->
                // Access layout coordinates and extract height in pixels
                val heightPx = coordinates.size.height
                // Convert pixels to Dp
//                rowHeightDp = with(density) { heightPx.toDp() }
                rowHeightDp = heightPx
                println("row height = $rowHeightDp")
            }
    ) {
     //   Box(modifier = Modifier.fillMaxSize()) {
            Canvas(
                modifier = Modifier
                    .weight(4f)
                    .border(width = 5.dp, color = Color.Black)
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
            ) {
                val canvasQuadrantSize = size / 4F //делим размер канваса на 2
                if (initialSize == Size.Zero) {
//                    initialSize = size
                    initialSize = rectSize
                }
//                scale.value = size.width / initialSize.width
                scale.value = rowHeightDp / initialSize.height
                val canvasWidth = size.width
                val canvasHeight = size.height
                scale(scale = scale.value, pivot = Offset(x = 0f, y = 0f)) {
                    println("initialSize = $initialSize, canvas size = $size")
                    drawImage(
                        //и выводим его на канвас
                        image = imageBitmap,
//                topLeft = Offset(x = 0f, y = 0f), //координаты верхнего
//                    topLeft = Offset(x = position.x, y = position.y), //координаты верхнего
                    )
                }
//        drawControl(canvasQuadrantSize, imageBitmap)
            }

     //   }
        var isRegisterOpened by remember { mutableStateOf(false) }
        Column(verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .offset(x = 10.dp, y = 10.dp)
                .weight(1f)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(
                    checked = isRegisterOpened,
                    onCheckedChange = {
                        isRegisterOpened = it
                    }
                )
                Text(text = "${if(isRegisterOpened)"Закрыть" else "Открыть"}  регистр",
                    modifier = Modifier
                        .clickable(onClick = { isRegisterOpened = !isRegisterOpened }))
            }
            var bitValue by remember { mutableStateOf(0) }
            Row(verticalAlignment = Alignment.CenterVertically,
                ) {
                BinaryPickerDropdown(isRegisterOpened)
//                TextField(
//                    value = bitValue.toString(),
//                    onValueChange = {
//                        if (it.isNotEmpty() && it.length==1)
//                            bitValue = it.toInt()
//                    },
////                    textAlign = TextAlign.Center,
//                    placeholder = { "_" },
//                    singleLine = true,
//                    modifier = Modifier
//                        .width(50.dp)
//                )
                Button(
                    onClick = {
                    },
                    enabled = isRegisterOpened
                ) {
                    Text(text = "Послать\n 1 бит",
//                        textStyle = TextStyle( //объект для изменения стиля текста
                            fontSize = 10.sp //увеличиваем шрифт
//                        ),
                    )
                }
                //todo add vertical row with 8 binary array digits
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BinaryPickerDropdown(isRegisterOpened: Boolean) {
    var value by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }

    Row {
//        OutlinedTextField(
        TextField(
            value = value.toString(),
            enabled = false,
            onValueChange = {},
            readOnly = true,
//            label = { Text("0 / 1") },
            trailingIcon = {
                    IconButton(onClick = { expanded = !expanded },
                        modifier = Modifier.scale(0.8f)) {
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                    }
            },
//            textStyle = TextStyle( //объект для изменения стиля текста
//                fontSize = 10.sp //увеличиваем шрифт
//            ),
//            trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
            modifier = Modifier
                .width(80.dp)
//                .fillMaxWidth()
                .clickable { expanded = !expanded }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = { value = 0; expanded = false }
            ){
                Text(text = "0")
            }
            DropdownMenuItem(
//                text = { Text("1") },
                onClick = { value = 1; expanded = false }
            ){
                Text(text = "1")
            }

        }
    }
   // Text("Selected: $value")
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