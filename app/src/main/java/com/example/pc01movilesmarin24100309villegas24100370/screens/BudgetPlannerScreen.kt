package com.example.pc01movilesmarin24100309villegas24100370.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetPlannerScreen(onBackClick: () -> Unit) {
    var daysInput by remember { mutableStateOf("") }
    var dailyBudgetInput by remember { mutableStateOf("") }
    
    val accommodationOptions = listOf(
        "Económico" to 0.8,
        "Estándar" to 1.0,
        "Premium" to 1.5
    )
    var selectedOption by remember { mutableStateOf(accommodationOptions[1]) } // Estándar por defecto
    var expanded by remember { mutableStateOf(false) }

    var totalBudget by remember { mutableStateOf<Double?>(null) }
    var errorDays by remember { mutableStateOf<String?>(null) }
    var errorBudget by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Planificador de Presupuesto") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
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
            Text(
                text = "Calcula tu presupuesto de viaje",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Cantidad de días
            OutlinedTextField(
                value = daysInput,
                onValueChange = {
                    daysInput = it
                    errorDays = null
                },
                label = { Text("Cantidad de días") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = errorDays != null,
                supportingText = {
                    if (errorDays != null) {
                        Text(text = errorDays!!, color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            // Presupuesto diario
            OutlinedTextField(
                value = dailyBudgetInput,
                onValueChange = {
                    dailyBudgetInput = it
                    errorBudget = null
                },
                label = { Text("Presupuesto diario (S/)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                isError = errorBudget != null,
                supportingText = {
                    if (errorBudget != null) {
                        Text(text = errorBudget!!, color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            // Tipo de alojamiento (DropdownMenu)
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedOption.first,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de alojamiento") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    accommodationOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.first) },
                            onClick = {
                                selectedOption = option
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }

            Button(
                onClick = {
                    val days = daysInput.toIntOrNull()
                    val budget = dailyBudgetInput.toDoubleOrNull()
                    
                    var hasError = false
                    
                    if (daysInput.isBlank()) {
                        errorDays = "Campo obligatorio"
                        hasError = true
                    } else if (days == null || days <= 0) {
                        errorDays = "Días deben ser mayor a cero"
                        hasError = true
                    }

                    if (dailyBudgetInput.isBlank()) {
                        errorBudget = "Campo obligatorio"
                        hasError = true
                    } else if (budget == null || budget <= 0) {
                        errorBudget = "Presupuesto debe ser mayor a cero"
                        hasError = true
                    }

                    if (!hasError) {
                        totalBudget = days!! * budget!! * selectedOption.second
                    } else {
                        totalBudget = null
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Calcular Presupuesto Total")
            }

            if (totalBudget != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Presupuesto Total Estimado",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "S/ ${"%.2f".format(totalBudget)}",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Para un viaje de $daysInput días con un presupuesto diario de S/ $dailyBudgetInput y alojamiento ${selectedOption.first.lowercase()}.",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    }
}
