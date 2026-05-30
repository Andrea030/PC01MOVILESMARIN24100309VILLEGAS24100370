package com.example.pc01movilesmarin24100309villegas24100370.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Luggage
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pc01movilesmarin24100309villegas24100370.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaggageCalculatorScreen(onBackClick: () -> Unit) {
    var weightInput by remember { mutableStateOf("") }
    var flightType by remember { mutableStateOf("Nacional") }
    val flightOptions = listOf("Nacional", "Internacional")

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var resultMessage by remember { mutableStateOf<String?>(null) }
    var isExceeded by remember { mutableStateOf(false) }
    var excessKg by remember { mutableStateOf(0.0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("EcoTraveler", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.05f)
                        )
                    )
                )
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Calculadora de Equipaje",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Peso Input
                    OutlinedTextField(
                        value = weightInput,
                        onValueChange = {
                            weightInput = it
                            errorMessage = null
                            resultMessage = null
                        },
                        label = { Text("Peso de la maleta (kg)") },
                        leadingIcon = { Icon(Icons.Default.Luggage, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = errorMessage != null,
                        supportingText = {
                            if (errorMessage != null) {
                                Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        shape = RoundedCornerShape(12.dp)
                    )

                    Divider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)

                    // Tipo de Vuelo
                    Text(
                        text = "Tipo de vuelo",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Column(Modifier.selectableGroup()) {
                        flightOptions.forEach { text ->
                            val selected = (text == flightType)
                            Surface(
                                onClick = { flightType = text },
                                shape = RoundedCornerShape(12.dp),
                                color = if (selected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surface,
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = selected,
                                        onClick = null
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = if (text == "Nacional") "$text (Máx. 23 kg)" else "$text (Máx. 32 kg)",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
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
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Calcular Equipaje", fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Resultados
            if (resultMessage != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isExceeded)
                            MaterialTheme.colorScheme.errorContainer
                        else
                            MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = if (isExceeded) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = resultMessage!!,
                                fontWeight = FontWeight.ExtraBold,
                                style = MaterialTheme.typography.titleMedium,
                                color = if (isExceeded) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            if (isExceeded) {
                                Text(
                                    text = "Exceso: ${"%.2f".format(excessKg)} kg",
                                    color = MaterialTheme.colorScheme.error,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
            
            TextButton(onClick = onBackClick) {
                Text("Volver al Menú Principal")
            }
        }
    }
}
