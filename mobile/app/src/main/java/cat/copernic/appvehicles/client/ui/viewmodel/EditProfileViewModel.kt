package cat.copernic.appvehicles.client.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cat.copernic.appvehicles.client.data.api.remote.ClientApiService
import cat.copernic.appvehicles.client.data.model.ClientUpdateRequest
import cat.copernic.appvehicles.client.data.repository.ClientRepository
import cat.copernic.appvehicles.core.auth.SessionStore
import cat.copernic.appvehicles.core.network.RetrofitProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class EditProfileUiState(
    val isLoading: Boolean = false,
    val dni: String? = null,
    val nomComplet: String = "",
    val email: String = "",
    val telefon: String = "",
    val adreca: String = "",
    val nacionalitat: String = "",
    val numeroTargetaCredit: String = "",
    val dataCaducitatDni: String = "",
    val tipusCarnetConduir: String = "",
    val dataCaducitatCarnet: String = "",
    val message: String? = null,
    val error: String? = null
)

class EditProfileViewModel(app: Application) : AndroidViewModel(app) {

    private val sessionStore = SessionStore(app.applicationContext)

    private val api: ClientApiService =
        RetrofitProvider.retrofit.create(ClientApiService::class.java)

    private val repo = ClientRepository(api)

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, message = null)

            val dni = sessionStore.dniFlow().first()
            if (dni.isNullOrBlank()) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = "No hi ha DNI guardat. Registra't.")
                return@launch
            }

            val response = repo.getClient(dni)
            if (response.isSuccessful && response.body() != null) {
                val p = response.body()!!
                _uiState.value = EditProfileUiState(
                    isLoading = false,
                    dni = p.dni,
                    nomComplet = p.nomComplet,
                    email = p.email,
                    telefon = p.telefon.orEmpty(),
                    adreca = p.adreca.orEmpty(),
                    nacionalitat = p.nacionalitat.orEmpty(),
                    numeroTargetaCredit = p.numeroTargetaCredit.orEmpty(),
                    dataCaducitatDni = p.dataCaducitatDni.orEmpty(),
                    tipusCarnetConduir = p.tipusCarnetConduir.orEmpty(),
                    dataCaducitatCarnet = p.dataCaducitatCarnet.orEmpty()
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = response.errorBody()?.string() ?: "Error carregant perfil (${response.code()})"
                )
            }
        }
    }

    fun onFieldChange(
        nomComplet: String? = null,
        telefon: String? = null,
        adreca: String? = null,
        nacionalitat: String? = null,
        numeroTargetaCredit: String? = null,
        dataCaducitatDni: String? = null,
        tipusCarnetConduir: String? = null,
        dataCaducitatCarnet: String? = null
    ) {
        val s = _uiState.value
        _uiState.value = s.copy(
            nomComplet = nomComplet ?: s.nomComplet,
            telefon = telefon ?: s.telefon,
            adreca = adreca ?: s.adreca,
            nacionalitat = nacionalitat ?: s.nacionalitat,
            numeroTargetaCredit = numeroTargetaCredit ?: s.numeroTargetaCredit,
            dataCaducitatDni = dataCaducitatDni ?: s.dataCaducitatDni,
            tipusCarnetConduir = tipusCarnetConduir ?: s.tipusCarnetConduir,
            dataCaducitatCarnet = dataCaducitatCarnet ?: s.dataCaducitatCarnet,
            message = null,
            error = null
        )
    }

    fun saveChanges() {
        viewModelScope.launch {
            val s = _uiState.value
            val dni = s.dni
            if (dni.isNullOrBlank()) {
                _uiState.value = s.copy(error = "DNI no disponible")
                return@launch
            }
            if (s.nomComplet.isBlank()) {
                _uiState.value = s.copy(error = "Nom complet obligatori")
                return@launch
            }

            _uiState.value = s.copy(isLoading = true, error = null, message = null)

            val req = ClientUpdateRequest(
                nomComplet = s.nomComplet,
                telefon = s.telefon.ifBlank { null },
                adreca = s.adreca.ifBlank { null },
                nacionalitat = s.nacionalitat.ifBlank { null },
                numeroTargetaCredit = s.numeroTargetaCredit.ifBlank { null },
                dataCaducitatDni = s.dataCaducitatDni.ifBlank { null },
                tipusCarnetConduir = s.tipusCarnetConduir.ifBlank { null },
                dataCaducitatCarnet = s.dataCaducitatCarnet.ifBlank { null }
            )

            val response = repo.updateClient(dni, req)
            if (response.isSuccessful) {
                _uiState.value = _uiState.value.copy(isLoading = false, message = "Canvis guardats correctament ✅")
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = response.errorBody()?.string() ?: "Error guardant (${response.code()})"
                )
            }
        }
    }
}