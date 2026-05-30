package com.example.pc01movilesmarin24100309villegas24100370

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pc01movilesmarin24100309villegas24100370.ui.theme.PC01MOVILESMARIN24100309VILLEGAS24100370Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PC01MOVILESMARIN24100309VILLEGAS24100370Theme {
                AppNavHost()
            }
        }
    }
}

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BaggageCalculatorScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun BaggageCalculatorScreen(modifier: Modifier = Modifier) {
    var weightInput by remember { mutableStateOf("") }
    var flightType by remember { mutableStateOf("Nacional") }
    val flightOptions = listOf("Nacional", "Internacional")

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var resultMessage by remember { mutableStateOf<String?>(null) }
    var isExceeded by remember { mutableStateOf(false) }
    var excessKg by remember { mutableStateOf(0.0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Calculadora de Equipaje",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Ingresar: Peso de la maleta
        OutlinedTextField(
            value = weightInput,
            onValueChange = {
                weightInput = it
                errorMessage = null // Limpiar error al editar
                resultMessage = null // Limpiar resultado al editar
            },
            label = { Text("Peso de la maleta (kg)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = errorMessage != null,
            supportingText = {
                if (errorMessage != null) {
                    Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
                }
            }
        )

        // Ingresar: Tipo de vuelo
        Text(
            text = "Tipo de vuelo",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Start)
        )

        Column(Modifier.selectableGroup()) {
            flightOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .selectable(
                            selected = (text == flightType),
                            onClick = { flightType = text },
                            role = Role.RadioButton
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == flightType),
                        onClick = null // El click lo maneja el Row
                    )
                    Text(
                        text = if (text == "Nacional") "$text (máximo 23 kg)" else "$text (máximo 32 kg)",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }

        // Botón
        Button(
            onClick = {
                val weight = weightInput.toDoubleOrNull()
                when {
                    weightInput.isBlank() -> {
                        errorMessage = "Campo obligatorio"
                        resultMessage = null
                    }
                    weight == null -> {
                        errorMessage = "Debe ser un valor numérico"
                        resultMessage = null
                    }
                    weight <= 0 -> {
                        errorMessage = "El peso debe ser mayor a cero"
                        resultMessage = null
                    }
                    else -> {
                        errorMessage = null
                        val limit = if (flightType == "Nacional") 23.0 else 32.0
                        if (weight <= limit) {
                            isExceeded = false
                            resultMessage = "Cumple el límite permitido ($limit kg)"
                        } else {
                            isExceeded = true
                            excessKg = weight - limit
                            resultMessage = "Excede el límite permitido ($limit kg)"
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular")
        }

        // Mostrar Resultados
        if (resultMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (isExceeded) 
                        MaterialTheme.colorScheme.errorContainer 
                    else 
                        MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = resultMessage!!,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    if (isExceeded) {
                        Text(
                            text = "Cantidad de kg excedidos: ${"%.2f".format(excessKg)} kg",
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BaggageCalculatorPreview() {
    PC01MOVILESMARIN24100309VILLEGAS24100370Theme {
        BaggageCalculatorScreen()
    }
}
