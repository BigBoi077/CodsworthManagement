package cegepst.example.codsworthmanagement.models

import cegepst.example.codsworthmanagement.stores.AppStore
import cegepst.example.codsworthmanagement.views.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VaultManager(mainActivity: MainActivity, vaultNumber: Long) {

    private val mainActivity = mainActivity
    private val database = mainActivity.let { AppStore(it) }
    private val vaultNumber: Long = vaultNumber

    private fun registerVault() {
        GlobalScope.launch {
            val vault = Vault(vaultNumber, 0, 0, 0, 0, 0)
            database.vaultDAO().insert(vault)
            withContext(Dispatchers.Main) {
                mainActivity.loadVault(vault)
            }
        }
    }

    fun handleVaultLoad() {
        var vault: Vault
        GlobalScope.launch {
            if (database.vaultDAO().get(vaultNumber) == null) {
                registerVault()
                return@launch
            }
            vault = database.vaultDAO().get(vaultNumber)
            withContext(Dispatchers.Main) {
                mainActivity.loadVault(vault)
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