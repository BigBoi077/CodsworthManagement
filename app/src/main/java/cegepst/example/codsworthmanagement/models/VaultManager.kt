package cegepst.example.codsworthmanagement.models

import cegepst.example.codsworthmanagement.stores.AppStore
import cegepst.example.codsworthmanagement.views.MainActivity

class VaultManager(mainActivity: MainActivity, vaultNumber: Long) {

    private val database = AppStore(mainActivity)
    private val vaultNumber: Long = vaultNumber

    fun registerVault(): Vault {
        val vault = Vault(0, 0, 0, 0, 0)
        database.vaultDAO().insert(vault)
        return vault
    }

    fun loadVaultContent(): Vault {
        return database.vaultDAO().get(vaultNumber)
    }

    fun vaultExist(): Boolean {
        return database.vaultDAO().get(vaultNumber) == null
    }
}