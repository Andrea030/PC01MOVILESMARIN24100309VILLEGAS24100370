package com.example.pc01movilesmarin24100309villegas24100370.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pc01movilesmarin24100309villegas24100370.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaggageCalculatorScreen(
    onBackClick: () -> Unit
) {
    var weightInput by remember { mutableStateOf("") }
    var flightType by remember { mutableStateOf("Nacional") }
    val flightOptions = listOf("Nacional", "Internacional")

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var resultMessage by remember { mutableStateOf<String?>(null) }
    var isExceeded by remember { mutableStateOf(false) }
    var excessKg by remember { mutableStateOf(0.0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Calculadora de Equipaje") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = weightInput,
                onValueChange = {
                    weightInput = it
                    errorMessage = null
                    resultMessage = null
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
                            onClick = null
                        )
                        Text(
                            text = if (text == "Nacional") "$text (máximo 23 kg)" else "$text (máximo 32 kg)",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }

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

            if (resultMessage != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isExceeded) {
                            MaterialTheme.colorScheme.errorContainer
                        } else {
                            MaterialTheme.colorScheme.primaryContainer
                        }
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

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.back_to_menu))
            }
        }
    }
}




