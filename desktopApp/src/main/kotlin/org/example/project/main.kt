package org.example.project

//import dev.snipme.highlights.Highlights
//import dev.snipme.highlights.model.PhraseLocation
//import dev.snipme.highlights.model.SyntaxLanguage
//import dev.snipme.highlights.model.SyntaxTheme
//import dev.snipme.highlights.model.SyntaxThemes
//import io.github.komodgn.codeview.compose.CodeView
//import io.github.komodgn.codeview.core.CodeLanguage

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.gallatinapps.syntaxmp.compose.SyntaxTheme
import com.gallatinapps.syntaxmp.compose.rememberSyntaxAnnotatedString
import com.gallatinapps.syntaxmp.tokenizer.SyntaxTokenizer
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.VerticalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState
import java.awt.Cursor

private fun Modifier.cursorForHorizontalResize(): Modifier =
    pointerHoverIcon(PointerIcon(Cursor(Cursor.E_RESIZE_CURSOR)))

//@Composable
//fun MainWindow() {
//  println("Hello world!")
//}

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
fun ResizableSplitWindow() {
    val splitterState = rememberSplitPaneState()
    val bitArray = mutableListOf<Boolean>()
    val ledArray = mutableListOf<Boolean>()
    for(i in 0..7) {
        bitArray.add(false)
        ledArray.add(false)
    }

    splitterState.moveEnabled = true
    splitterState.positionPercentage = 0.8F
   VerticalSplitPane (
        splitPaneState = splitterState,
        modifier = Modifier
            .fillMaxSize()
//            .border(5.dp, Color.Gray)
    ) {
        first(150.dp) {
            Column(Modifier
                .fillMaxSize()
                .border(5.dp, Color.Gray)
            ) {
//                Text("Upper Panel", modifier = Modifier.wrapContentSize())
                ShiftWorkArea(bitArray, ledArray)
//                CanvasWithButton()
            }
        }

        second(140.dp) {
            Column(Modifier.fillMaxSize()) {
                var text = """
     int DS_pin = 8; //назначаем пины 8,9,10 для управления
     int STCP_pin = 9; //сдвиговым регистром
     int SHCP_pin = 10;
     
     void writereg() {//функция для передачи данных на сдвиговый регистр
        //она позволяет синхронизировать передачу данных на сдвиговый регистр
        digitalWrite(STCP_pin, LOW); //открываем сдвиговый регистр на прием данных
        for (int i = 7; i >= 0; i--){
          digitalWrite(SHCP_pin, LOW); //открываем порт регистра для приема 1 значения
          digitalWrite(DS_pin, registers[i] ); //посылаем логическое значение в
                                                            //сдвиговый регистр
          digitalWrite(SHCP_pin, HIGH); //закрываем порт регистра для приема
        }
        digitalWrite(STCP_pin, HIGH); //закрываем сдвиговый регистр
     }
                """.trimIndent() //todo загружать код из файла ,продумать работу с каждой строкой, чтобы можно было подчеркивать
//                Text("Bottom Panel", modifier = Modifier.weight(1f))
                val eng = SyntaxTokenizer()
//                eng.
                CodeSnippet(text, "CPP", eng, SyntaxTheme.DefaultLight)
            }
        }
//        splitter {
//            visiblePart {
//                Box(
//                    Modifier
//                        .width(1.dp)
//                        .fillMaxWidth()
//                        .background(MaterialTheme.colors.background)
//                )
//            }
//            handle {
//                Box(
//                    Modifier
//                        .markAsHandle()
//                        .cursorForHorizontalResize()
//                        .background(SolidColor(Color.Gray), alpha = 0.50f)
//                        .width(9.dp)
//                        .fillMaxWidth()
//                )
//            }
//        }
    }
}

@Composable
fun CodeSnippet(
    code: String,
    languageLabel: String?,
    engine: SyntaxTokenizer,
    theme: SyntaxTheme,
) {
    BasicText(
        text = rememberSyntaxAnnotatedString(
            code = code,
            languageLabel = languageLabel,
            engine = engine,
            theme = theme,
        ),
        style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 14.sp),
    )
}


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ShiftRegisterVisualizer",
        state = WindowState(size = DpSize(1000.dp, 800.dp))
    ) {
//        App()
//        MainWindow()// { /*println("Closed") */}
        ResizableSplitWindow()
    }
}