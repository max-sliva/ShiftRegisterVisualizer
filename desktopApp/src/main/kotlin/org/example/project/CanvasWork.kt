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
import shiftregistervisualizer.desktopapp.generated.resources.Res
import shiftregistervisualizer.desktopapp.generated.resources.withBreadBoard

@Composable
fun ShiftWorkArea(bitArray: MutableList<Boolean>) {
//    val bitArray = mutableListOf<Boolean>()
//    for(i in 0..7) bitArray.add(false)
//    val bitArray = BooleanArray(8)
    var bitNumber by remember { mutableStateOf(0) }

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
//    var bitValue by remember { mutableStateOf(0) }
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 20.dp)
            .border(width = 5.dp, color = Color.Black)
            .onGloballyPositioned { coordinates -> //для получения высоты ряда, чтобы скейлить картинку
                // Access layout coordinates and extract height in pixels
                val heightPx = coordinates.size.height
                rowHeightDp = heightPx
                println("row height = $rowHeightDp")
            }
    ) {
            Canvas(
                modifier = Modifier
                    .weight(4f)
                    .border(width = 5.dp, color = Color.Black)
            ) {
                val canvasQuadrantSize = size / 4F //делим размер канваса на 2
                if (initialSize == Size.Zero) {
//                    initialSize = size
                    initialSize = rectSize
                }
//                scale.value = size.width / initialSize.width
                scale.value = (rowHeightDp-100) / initialSize.height
                val canvasWidth = size.width
                val canvasHeight = size.height
                scale(scale = scale.value, pivot = Offset(x = 0f, y = 0f)) {
                    println("initialSize = $initialSize, canvas size = $size")
                    drawImage(
                        //и выводим его на канвас
                        image = imageBitmap,
//                        style = DrawStyle.
//                topLeft = Offset(x = 0f, y = 0f), //координаты верхнего
//                    topLeft = Offset(x = position.x, y = position.y), //координаты верхнего
                    )
                }
//        drawControl(canvasQuadrantSize, imageBitmap)
            }

     //   }
            Row(
                modifier = Modifier
                    .weight(1f)
            ){
                var isRegisterOpened by remember { mutableStateOf(false) }
                Column {
                    bitArray.forEachIndexed { index, value->
                        Text(
                            text = if(value)"1" else "0",
                            modifier = Modifier
                                .border(width = 1.dp, color = if (index!=bitNumber || !isRegisterOpened) Color.Black else Color.Green)
                                .padding(5.dp)
                        )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .offset(x = 10.dp, y = 10.dp)
//                        .weight(1f)
                ) {
                    var switchIsEnabled by remember { mutableStateOf(true) }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Switch(
                            checked = isRegisterOpened,
                            onCheckedChange = {
                                if (isRegisterOpened) {
                                    println("Send bit array to register")
                                }
                                isRegisterOpened = it
                                bitNumber = 0
                                if (isRegisterOpened) switchIsEnabled = bitNumber >= 7
                            },
                            enabled = switchIsEnabled,
                        )
                        Text(
                            text = "${if (isRegisterOpened) "Закрыть" else "Открыть"}  регистр",
                            modifier = Modifier
                                .clickable(
                                    onClick = {
                                        if (switchIsEnabled) {
                                            isRegisterOpened = !isRegisterOpened
                                            bitNumber = 0
                                        }
                                    }
                                )
                        )
                    }
                    var bitValue = remember { mutableStateOf(0) }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        BinaryPickerDropdown(isRegisterOpened, bitValue)
                        Button(
                            onClick = {
                                bitArray[bitNumber++] = if (bitValue.value==1) true else false
                                println("${bitArray}, bitNumber = $bitNumber, isRegisterOpened = $isRegisterOpened")
                                if (isRegisterOpened) switchIsEnabled = bitNumber == 8
                            },
                            enabled = isRegisterOpened && bitNumber<8
                        ) {
                            Text(
                                text = "Послать\n 1 бит",
                                fontSize = 10.sp //увеличиваем шрифт
                            )
                        }
                    }
                }
            }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BinaryPickerDropdown(isRegisterOpened: Boolean, bitValue: MutableState<Int>) {
//    var value by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }

    Row {
//        OutlinedTextField(
        TextField(
            value = bitValue.value.toString(),
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
                onClick = { bitValue.value = 0; expanded = false }
            ){
                Text(text = "0")
            }
            DropdownMenuItem(
//                text = { Text("1") },
                onClick = { bitValue.value = 1; expanded = false }
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

