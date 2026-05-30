package com.example.pc01movilesmarin24100309villegas24100370

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pc01movilesmarin24100309villegas24100370.screens.BaggageCalculatorScreen
import com.example.pc01movilesmarin24100309villegas24100370.screens.BudgetPlannerScreen
import com.example.pc01movilesmarin24100309villegas24100370.screens.DestinationsCatalogScreen
import com.example.pc01movilesmarin24100309villegas24100370.screens.LocationPermissionScreen
import com.example.pc01movilesmarin24100309villegas24100370.screens.MainMenuScreen

private object AppRoute {
    const val MainMenu = "main_menu"
    const val BaggageCalculator = "baggage_calculator"
    const val BudgetPlanner = "budget_planner"
    const val DestinationsCatalog = "destinations_catalog"
    const val LocationPermission = "location_permission"
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoute.MainMenu
    ) {
        composable(AppRoute.MainMenu) {
            MainMenuScreen(
                onBaggageCalculatorClick = { navController.navigate(AppRoute.BaggageCalculator) },
                onBudgetPlannerClick = { navController.navigate(AppRoute.BudgetPlanner) },
                onDestinationsCatalogClick = { navController.navigate(AppRoute.DestinationsCatalog) },
                onLocationPermissionClick = { navController.navigate(AppRoute.LocationPermission) }
            )
        }

        composable(AppRoute.BaggageCalculator) {
            BaggageCalculatorScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(AppRoute.BudgetPlanner) {
            BudgetPlannerScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(AppRoute.DestinationsCatalog) {
            DestinationsCatalogScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(AppRoute.LocationPermission) {
            LocationPermissionScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

