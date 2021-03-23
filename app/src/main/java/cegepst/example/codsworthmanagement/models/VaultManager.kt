package cegepst.example.codsworthmanagement.models

import cegepst.example.codsworthmanagement.stores.AppStore
import kotlinx.coroutines.*

class VaultManager(appStore: AppStore, vaultNumber: Long) {

    private val database = appStore
    private val vaultNumber: Long = vaultNumber

    private fun registerVault(lambda: (Vault) -> Unit) {
        GlobalScope.launch {
            val vault = Vault(vaultNumber,
                    0, 0, 0, 0, 0,
                    false, false, false,
                    0.0, 0.0, 0.0)
            database.vaultDAO().insert(vault)
            withContext(Dispatchers.Main) {
                lambda(vault)
            }
        }
    }

    fun handleVaultLoad(lambda: (Vault) -> Unit) {
        var vault: Vault
        GlobalScope.launch {
            if (database.vaultDAO().get(vaultNumber) == null) {
                registerVault(lambda)
                return@launch
            }
            vault = database.vaultDAO().get(vaultNumber)
            withContext(Dispatchers.Main) {
                lambda(vault)
            }
        }
    }

    fun delete(vaultNumber: Long) {
        GlobalScope.launch {
            database.vaultDAO().delete(vaultNumber)
            withContext(Dispatchers.Main) {
                return@withContext
            }
        }
    }
}