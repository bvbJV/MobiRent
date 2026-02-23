
package cat.copernic.appvehicles.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.copernic.appvehicles.usuariAnonim.ui.view.HomeScreen
// Importa tus otras pantallas aquí (ReservesScreen, PerfilScreen, etc.)
import cat.copernic.appvehicles.reserva.ui.view.ReserveListScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { AppBottomNavigation(navController) }
    ) { paddingValues ->
        // El NavHost decide qué pantalla mostrar según la ruta
        NavHost(
            navController = navController,
            startDestination = AppRoutes.Inici.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(AppRoutes.Inici.route) {
                HomeScreen(onVehicleClick = { vehicleId ->
                    // Aquí manejarás más adelante la navegación al detalle del vehículo
                })
            }
            composable(AppRoutes.Reserves.route) {
                // ReservesScreen() -> Crea esta función en reserva/ui/view
                ReserveListScreen()
            }

            composable(AppRoutes.Perfil.route) {
                // PerfilScreen() -> Crea esta función en client/ui/view
            }
        }
    }
}