package cegepst.example.codsworthmanagement.controllers

import android.widget.TextView
import android.widget.Toast
import cegepst.example.codsworthmanagement.R
import cegepst.example.codsworthmanagement.models.Constants
import cegepst.example.codsworthmanagement.models.Vault
import cegepst.example.codsworthmanagement.stores.AppStore
import cegepst.example.codsworthmanagement.views.MainActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class MainController(mainActivity: MainActivity, vault: Vault) {

    private var vault = vault
    private var weakReference = WeakReference(mainActivity)
    private val database = AppStore(mainActivity)
    private val screenManager = ScreenController(weakReference, vault)


    private lateinit var capsView: TextView
    private lateinit var waterView: TextView
    private lateinit var steakView: TextView
    private lateinit var colaView: TextView

    fun initVariables() {
        capsView = weakReference.get()!!.findViewById(R.id.quantityCaps)
        waterView = weakReference.get()!!.findViewById(R.id.quantityWater)
        steakView = weakReference.get()!!.findViewById(R.id.quantitySteak)
        colaView = weakReference.get()!!.findViewById(R.id.quantityCola)
    }

    fun loadContent() {
        GlobalScope.launch {
            vault = database.vaultDAO().get(vault.id)
            capsView.text = "${vault.nbrCaps} caps"
            waterView.text = "${vault.waterUpgrades} water"
            steakView.text = "${vault.steakUpgrades} steaks"
            colaView.text = "${vault.nukaColaUpgrades} cola"
        }
    }

    fun updateContent() {
        GlobalScope.launch {
            database.vaultDAO().update(vault)
            loadContent()
        }
    }

    fun updateButtons() {
        screenManager.lockAccordingButtons()
    }

    fun buyWater() {
        if (vault.hasBoughtWater) {
            alert("You have already bought water, maybe try upgrading")
        } else {
            if (hasEnoughtMoney(Constants.waterInitialCost)) {
                vault.hasBoughtWater = true
            }
        }
    }

    private fun hasEnoughtMoney(wantedCapsQuantity: Int): Boolean {
        return vault.nbrCaps >= wantedCapsQuantity
    }

    fun upgradeWater() {

    }

    fun alert(message: String) {
        Toast.makeText(weakReference.get(), message, Toast.LENGTH_SHORT)
    }
}