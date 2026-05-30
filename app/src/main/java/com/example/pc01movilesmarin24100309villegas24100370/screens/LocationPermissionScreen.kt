package com.example.pc01movilesmarin24100309villegas24100370.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.LocationOff
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionStatus = if (isGranted) PermissionStatus.Granted else PermissionStatus.Denied
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.location_permission_title),
                        fontWeight = FontWeight.Bold
                    )
                },
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
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
                        )
                    )
                )
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.size(80.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MyLocation,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Permisos de Ubicación",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Para ofrecerte una mejor asistencia durante tu viaje, necesitamos acceso a tu ubicación actual.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            StatusCard(permissionStatus)

            Spacer(modifier = Modifier.weight(1f))

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION) },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    enabled = permissionStatus !is PermissionStatus.Granted
                ) {
                    Icon(Icons.Default.GpsFixed, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ubicación Precisa")
                }

                OutlinedButton(
                    onClick = { requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION) },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    enabled = permissionStatus !is PermissionStatus.Granted
                ) {
                    Text("Ubicación Aproximada")
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = onBackClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.back_to_menu))
                }
            }
        }
    }
}

@Composable
fun StatusCard(status: PermissionStatus) {
    val containerColor = when (status) {
        is PermissionStatus.Granted -> MaterialTheme.colorScheme.primaryContainer
        is PermissionStatus.Denied -> MaterialTheme.colorScheme.errorContainer
        is PermissionStatus.Pending -> MaterialTheme.colorScheme.surfaceVariant
    }
    
    val contentColor = when (status) {
        is PermissionStatus.Granted -> MaterialTheme.colorScheme.onPrimaryContainer
        is PermissionStatus.Denied -> MaterialTheme.colorScheme.onErrorContainer
        is PermissionStatus.Pending -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    val icon = when (status) {
        is PermissionStatus.Granted -> Icons.Default.MyLocation
        is PermissionStatus.Denied -> Icons.Default.LocationOff
        is PermissionStatus.Pending -> Icons.Default.MyLocation
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = when (status) {
                        is PermissionStatus.Granted -> "✓ Acceso Concedido"
                        is PermissionStatus.Denied -> "✗ Acceso Denegado"
                        is PermissionStatus.Pending -> "⏳ Pendiente"
                    },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = contentColor
                )
                Text(
                    text = when (status) {
                        is PermissionStatus.Granted -> "Asistencia personalizada activada."
                        is PermissionStatus.Denied -> "Cambia esto en Configuración."
                        is PermissionStatus.Pending -> "Solicita acceso para continuar."
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = contentColor.copy(alpha = 0.8f)
                )
            }
        }
    }
}


