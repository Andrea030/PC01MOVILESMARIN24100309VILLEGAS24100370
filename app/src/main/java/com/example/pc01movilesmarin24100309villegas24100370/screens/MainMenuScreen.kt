package com.example.pc01movilesmarin24100309villegas24100370.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pc01movilesmarin24100309villegas24100370.R
//hola
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(
    onBaggageCalculatorClick: () -> Unit,
    onBudgetPlannerClick: () -> Unit,
    onDestinationsCatalogClick: () -> Unit,
    onLocationPermissionClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.main_menu_title)) }
            )
        }
    ) { innerPadding ->
        MainMenuContent(
            contentPadding = innerPadding,
            onBaggageCalculatorClick = onBaggageCalculatorClick,
            onBudgetPlannerClick = onBudgetPlannerClick,
            onDestinationsCatalogClick = onDestinationsCatalogClick,
            onLocationPermissionClick = onLocationPermissionClick
        )
    }
}

@Composable
private fun MainMenuContent(
    contentPadding: PaddingValues,
    onBaggageCalculatorClick: () -> Unit,
    onBudgetPlannerClick: () -> Unit,
    onDestinationsCatalogClick: () -> Unit,
    onLocationPermissionClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = onBaggageCalculatorClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.menu_baggage_calculator))
        }

        Button(
            onClick = onBudgetPlannerClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.menu_budget_planner))
        }

        Button(
            onClick = onDestinationsCatalogClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.menu_destinations_catalog))
        }

        Button(
            onClick = onLocationPermissionClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.menu_location_permission))
        }
    }
}


