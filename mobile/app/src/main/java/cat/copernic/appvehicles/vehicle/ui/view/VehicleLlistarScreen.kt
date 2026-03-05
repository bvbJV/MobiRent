package cat.copernic.appvehicles.vehicle.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import cat.copernic.appvehicles.R
import cat.copernic.appvehicles.model.VehicleMock
import cat.copernic.appvehicles.ui.theme.AppVehiclesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleLlistarScreen(
    onVehicleClick: (Int) -> Unit,
    onBackClick: () -> Unit = {}
) {

    // -----------------------------
    // Estados
    // -----------------------------
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }
    var ordenAscendente by remember { mutableStateOf(true) }
    var aplicarFiltro by remember { mutableStateOf(false) }

    // -----------------------------
    // Datos simulados
    // -----------------------------
    val vehiculos = listOf(
        VehicleMock(1, "Tesla", "Model 3", stringResource(R.string.electric), 25.0),
        VehicleMock(2, "Toyota", "Corolla", stringResource(R.string.hybrid), 18.5),
        VehicleMock(3, "BMW", "X1", stringResource(R.string.diesel), 30.0)
    )

    // -----------------------------
    // Lógica ordenación
    // -----------------------------
    val vehiculosOrdenados = if (ordenAscendente) {
        vehiculos.sortedBy { it.preuHora }
    } else {
        vehiculos.sortedByDescending { it.preuHora }
    }

    // -----------------------------
    // Lógica filtrado fechas (mock)
    // -----------------------------
    val vehiculosFinal = if (aplicarFiltro && fechaInicio.isNotBlank() && fechaFin.isNotBlank()) {
        // Simple simulation (real backend will handle this)
        vehiculosOrdenados.take(2)
    } else {
        vehiculosOrdenados
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.available_vehicles)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Go back")
                    }
                }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            // -----------------------------
            // DATE FILTER
            // -----------------------------
            item {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = stringResource(R.string.filter_by_date_range),
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = fechaInicio,
                            onValueChange = { fechaInicio = it },
                            label = { Text(stringResource(R.string.start_date_dd_mm_yyyy)) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = fechaFin,
                            onValueChange = { fechaFin = it },
                            label = { Text(stringResource(R.string.end_date_dd_mm_yyyy)) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = { aplicarFiltro = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(stringResource(R.string.apply_filter))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // -----------------------------
            // SORT BY PRICE
            // -----------------------------
            item {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = stringResource(R.string.sort_by_price),
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                            FilterChip(
                                selected = ordenAscendente,
                                onClick = { ordenAscendente = true },
                                label = { Text(stringResource(R.string.ascending)) }
                            )

                            FilterChip(
                                selected = !ordenAscendente,
                                onClick = { ordenAscendente = false },
                                label = { Text(stringResource(R.string.descending)) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // -----------------------------
            // VEHICLE LIST
            // -----------------------------
            items(vehiculosFinal) { vehiculo ->

                VehicleCard(
                    vehicle = vehiculo,
                    onClick = {
                        onVehicleClick(vehiculo.id)
                    }
                )
            }
        }
    }
}

@Composable
fun VehicleCard(vehicle: VehicleMock, onClick: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "${vehicle.marca} ${vehicle.model}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = vehicle.variant,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(R.string.hour, vehicle.preuHora),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VehicleListUnifiedScreenPreview() {
    AppVehiclesTheme {
        VehicleLlistarScreen(
            onVehicleClick = {}
        )
    }
}