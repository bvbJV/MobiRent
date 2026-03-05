package cat.copernic.appvehicles.core.auth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "session_store")

class SessionStore(private val context: Context) {

    private val KEY_DNI = stringPreferencesKey("client_dni")

    fun dniFlow(): Flow<String?> =
        context.dataStore.data.map { prefs -> prefs[KEY_DNI] }

    suspend fun saveDni(dni: String) {
        context.dataStore.edit { prefs -> prefs[KEY_DNI] = dni }
    }

    suspend fun clear() {
        context.dataStore.edit { prefs -> prefs.remove(KEY_DNI) }
    }
}