package cegepst.example.codsworthmanagement.controllers

import cegepst.example.codsworthmanagement.models.Vault
import cegepst.example.codsworthmanagement.stores.AppStore
import cegepst.example.codsworthmanagement.views.MainActivity
import java.lang.ref.WeakReference

class MainController(mainActivity: MainActivity, vault: Vault) {

    private var vault = vault
    private var weakReference = WeakReference(mainActivity)
    private val database = AppStore(mainActivity)

}