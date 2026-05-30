package com.example.pc01movilesmarin24100309villegas24100370.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pc01movilesmarin24100309villegas24100370.R

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
    var selectedOption by remember { mutableStateOf(accommodationOptions[1]) }
    var expanded by remember { mutableStateOf(false) }

    var totalBudget by remember { mutableStateOf<Double?>(null) }
    var errorDays by remember { mutableStateOf<String?>(null) }
    var errorBudget by remember { mutableStateOf<String?>(null) }

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
                text = "Planificador de Presupuesto",
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
                    // Cantidad de días
                    OutlinedTextField(
                        value = daysInput,
                        onValueChange = {
                            daysInput = it
                            errorDays = null
                        },
                        label = { Text("Cantidad de días") },
                        leadingIcon = { Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = errorDays != null,
                        supportingText = {
                            if (errorDays != null) {
                                Text(text = errorDays!!, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Presupuesto diario
                    OutlinedTextField(
                        value = dailyBudgetInput,
                        onValueChange = {
                            dailyBudgetInput = it
                            errorBudget = null
                        },
                        label = { Text("Presupuesto diario (S/)") },
                        leadingIcon = { Icon(Icons.Default.Payments, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        isError = errorBudget != null,
                        supportingText = {
                            if (errorBudget != null) {
                                Text(text = errorBudget!!, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Tipo de alojamiento
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
                            leadingIcon = { Icon(Icons.Default.Hotel, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier
                                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
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
                                    }
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
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Calcular Presupuesto", fontWeight = FontWeight.Bold)
                    }
                }
            }

            if (totalBudget != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Presupuesto Estimado",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        Text(
                            text = "S/ ${"%.2f".format(totalBudget)}",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Text(
                            text = "Para $daysInput días en categoría ${selectedOption.first.lowercase()}.",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            TextButton(onClick = onBackClick) {
                Text("Volver al Menú Principal")
            }
        }
    }
}
