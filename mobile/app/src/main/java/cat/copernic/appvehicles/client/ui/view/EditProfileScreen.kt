package cat.copernic.appvehicles.client.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.copernic.appvehicles.client.ui.viewmodel.EditProfileViewModel

@Composable
fun EditProfileScreen() {
    val vm: EditProfileViewModel = viewModel()
    val state by vm.uiState.collectAsState()

    LaunchedEffect(Unit) { vm.loadProfile() }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text("Editar perfil", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(12.dp))

        if (state.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(12.dp))
        }

        state.error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
            Spacer(Modifier.height(8.dp))
        }

        state.message?.let {
            Text(it, color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(8.dp))
        }

        OutlinedTextField(
            value = state.nomComplet,
            onValueChange = { vm.onFieldChange(nomComplet = it) },
            label = { Text("Nom complet *") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = state.email,
            onValueChange = { /* read-only */ },
            label = { Text("Correu electrònic (no editable)") },
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = state.telefon,
            onValueChange = { vm.onFieldChange(telefon = it) },
            label = { Text("Telèfon") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = state.adreca,
            onValueChange = { vm.onFieldChange(adreca = it) },
            label = { Text("Adreça") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = state.nacionalitat,
            onValueChange = { vm.onFieldChange(nacionalitat = it) },
            label = { Text("Nacionalitat") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))
        Text("Documentació", style = MaterialTheme.typography.titleMedium)

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = state.dataCaducitatDni,
            onValueChange = { vm.onFieldChange(dataCaducitatDni = it) },
            label = { Text("Caducitat DNI (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = state.tipusCarnetConduir,
            onValueChange = { vm.onFieldChange(tipusCarnetConduir = it) },
            label = { Text("Tipus carnet conduir") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = state.dataCaducitatCarnet,
            onValueChange = { vm.onFieldChange(dataCaducitatCarnet = it) },
            label = { Text("Caducitat carnet (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))
        Text("Pagament", style = MaterialTheme.typography.titleMedium)

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = state.numeroTargetaCredit,
            onValueChange = { vm.onFieldChange(numeroTargetaCredit = it) },
            label = { Text("Targeta de crèdit") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { vm.saveChanges() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        ) {
            Text("Guardar canvis")
        }
    }
}