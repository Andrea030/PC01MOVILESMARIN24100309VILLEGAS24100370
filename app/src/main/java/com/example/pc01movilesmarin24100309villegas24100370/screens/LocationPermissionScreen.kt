package com.example.pc01movilesmarin24100309villegas24100370.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pc01movilesmarin24100309villegas24100370.R

sealed class PermissionStatus {
    object Pending : PermissionStatus()
    object Granted : PermissionStatus()
    object Denied : PermissionStatus()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationPermissionScreen(onBackClick: () -> Unit) {
    var permissionStatus by remember { mutableStateOf<PermissionStatus>(PermissionStatus.Pending) }

    // Activity Result Contract para solicitar permiso de ubicación
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionStatus = if (isGranted) {
            PermissionStatus.Granted
        } else {
            PermissionStatus.Denied
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.location_permission_title)) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Permisos de Ubicación para Asistencia de Viaje",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Para ofrecerte una mejor asistencia durante tu viaje, necesitamos acceso a tu ubicación actual.",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Card mostrando el estado del permiso
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = when (permissionStatus) {
                        is PermissionStatus.Granted -> MaterialTheme.colorScheme.primaryContainer
                        is PermissionStatus.Denied -> MaterialTheme.colorScheme.errorContainer
                        is PermissionStatus.Pending -> MaterialTheme.colorScheme.surfaceVariant
                    }
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = when (permissionStatus) {
                            is PermissionStatus.Granted -> "✓ Permiso Concedido"
                            is PermissionStatus.Denied -> "✗ Permiso Denegado"
                            is PermissionStatus.Pending -> "⏳ Permiso Pendiente"
                        },
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (permissionStatus) {
                            is PermissionStatus.Granted -> MaterialTheme.colorScheme.onPrimaryContainer
                            is PermissionStatus.Denied -> MaterialTheme.colorScheme.onErrorContainer
                            is PermissionStatus.Pending -> MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = when (permissionStatus) {
                            is PermissionStatus.Granted -> "Se permite acceso a tu ubicación precisa para obtener asistencia personalizada de viaje."
                            is PermissionStatus.Denied -> "El permiso fue denegado. Puedes cambiar esto en Configuración > Permisos."
                            is PermissionStatus.Pending -> "Por favor, selecciona una opción abajo para solicitar acceso a tu ubicación."
                        },
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        color = when (permissionStatus) {
                            is PermissionStatus.Granted -> MaterialTheme.colorScheme.onPrimaryContainer
                            is PermissionStatus.Denied -> MaterialTheme.colorScheme.onErrorContainer
                            is PermissionStatus.Pending -> MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botones de acción
            Button(
                onClick = {
                    // Solicitar permiso de FINE LOCATION (GPS precisión alta)
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = permissionStatus !is PermissionStatus.Granted
            ) {
                Text("Solicitar Permiso de Ubicación Precisa")
            }

            Button(
                onClick = {
                    // Solicitar permiso de COARSE LOCATION (ubicación aproximada)
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = permissionStatus !is PermissionStatus.Granted
            ) {
                Text("Solicitar Permiso de Ubicación Aproximada")
            }

            if (permissionStatus is PermissionStatus.Denied) {
                Text(
                    text = "Nota: Si deseas cambiar de opinión, abre Configuración > Aplicaciones > PC01_MOVILES > Permisos.",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 12.dp),
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.back_to_menu))
            }
        }
    }
}


