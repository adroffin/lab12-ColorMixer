package com.example.lab12_colormixer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import com.example.lab12_colormixer.ui.theme.Lab12ColorMixerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorMixerScreen()
        }
    }
}

// Funcion en la que se crean los estados para el state hoisting y pasarlos a ColorMixer().
@Composable
fun ColorMixerScreen(){
    var colorRed by remember { mutableStateOf(0f)}
    var colorGreen by remember { mutableStateOf(0f)} // Se usan 3 estados, uno para cada color debido a que sus valores iran cambiando.
    var colorBlue by remember { mutableStateOf(0f) }

    ColorMixer(
        colorRed = colorRed,
        colorGreen = colorGreen,
        colorBlue = colorBlue
    ) { newRed, newGreen, newBlue ->
        colorRed = newRed
        colorGreen = newGreen
        colorBlue = newBlue
    }
}

// Pantalla que se le mostrara al usuario con una TopAppBar para el titulo, una Box para ver como se combinan los colores y los 3 sliders.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorMixer(
    colorRed: Float,
    colorGreen: Float,
    colorBlue: Float,
    onColorChange: (Float, Float, Float) -> Unit // Función para actualizar el estado
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TopAppBar que muestra el titulo
        TopAppBar(title = {Text("Color Mixer")})

        // Box que muesta el cambio de colores
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.25f)
                .background(Color(colorRed, colorGreen, colorBlue), RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Llamada de los 3 diferentes sliders, uno para cada color.
        ColorSlider("Red", colorRed) { onColorChange(it, colorGreen, colorBlue) }
        ColorSlider("Green", colorGreen) { onColorChange(colorRed, it, colorBlue) }
        ColorSlider("Blue", colorBlue) { onColorChange(colorRed, colorGreen, it) }
    }
}

// ColorSlider utilizando el componente Slider de material3.
// Utiliza la etiqueta del color que se le esta dando, el valor y el valor nuevo para ir desplegando los valores de 0 a 255.
@Composable
fun ColorSlider(label: String, value: Float, onValueChange: (Float) -> Unit){
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text("$label: ${(value * 255).toInt()}", style = MaterialTheme.typography.bodyLarge)
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..1f,
            colors = SliderDefaults.colors(
                thumbColor = when (label) {
                    "Red" -> Color.Red
                    "Green" -> Color.Green
                    "Blue" -> Color.Blue
                    else -> Color.Gray
                }
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Lab12ColorMixerTheme {
        ColorMixerScreen()
    }
}