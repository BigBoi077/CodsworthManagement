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

private const val PRODUCTION_TIME_RATIO = 0.0

class MainController(mainActivity: MainActivity, vault: Vault) {

    private var vault = vault
    private var weakReference = WeakReference(mainActivity)
    private val database = AppStore(mainActivity)
    private val screenManager = ScreenController(weakReference, vault)
    private val disposableController = DisposableController(this, vault)

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

    fun refreshContent() {
        GlobalScope.launch {
            vault = database.vaultDAO().get(vault.id)
            capsView.text = "${vault.nbrCaps} caps"
            waterView.text = "${vault.waterUpgrades} water"
            steakView.text = "${vault.steakUpgrades} steaks"
            colaView.text = "${vault.nukaColaUpgrades} cola"
        }
    }

    fun saveVault() {
        GlobalScope.launch {
            database.vaultDAO().update(vault)
            refreshContent()
        }
    }

    fun updateButtons() {
        screenManager.lockAccordingButtons()
    }

    private fun hasEnoughMoney(wantedCapsQuantity: Int): Boolean {
        return vault.nbrCaps >= wantedCapsQuantity
    }

    private fun alert(message: String) {
        Toast.makeText(weakReference.get(), message, Toast.LENGTH_SHORT).show()
    }

    private fun calculateResourceFee(modificationPrice: Int, nbrUpgrades: Int): Int {
        return modificationPrice * nbrUpgrades
    }

    private fun maxedOutUpgrades(wantedNbrUpgrades: Int): Boolean {
        return wantedNbrUpgrades > Constants.maxAmelioration
    }

    private fun calculateDelay(productionTime: Int, nbrUpgrades: Int): Double {
        return productionTime - (productionTime * (nbrUpgrades * PRODUCTION_TIME_RATIO))
    }

    fun buyWater() {
        if (vault.hasBoughtWater) {
            alert("You have already bought water, maybe try upgrading")
        } else {
            if (hasEnoughMoney(Constants.waterInitialCost)) {
                vault.hasBoughtWater = true
            }
        }
    }

    fun upgradeWater() {
        if (!vault.hasBoughtWater) {
            alert("You have not bought water yet")
        } else {
            if (maxedOutUpgrades(vault.waterUpgrades++)) {
                alert("This resource is maxed out")
                return
            }
            if (hasEnoughMoney(calculateResourceFee(Constants.waterModificationPrice, vault.waterUpgrades))) {
                vault.waterUpgrades++
                vault.waterCollectDelay = calculateDelay(Constants.waterProductionTime, vault.waterUpgrades)
            }
        }
    }

    fun buySteak() {
        if (vault.hasBoughtSteak) {
            alert("You have already bought water, maybe try upgrading")
        } else {
            if (hasEnoughMoney(Constants.steakInitialCost)) {
                vault.hasBoughtSteak = true
            }
        }
    }

    fun upgradeSteak() {
        if (!vault.hasBoughtSteak) {
            alert("You have not bought steak yet")
        } else {
            if (maxedOutUpgrades(vault.steakUpgrades++)) {
                alert("This resource is maxed out")
                return
            }
            if (hasEnoughMoney(calculateResourceFee(Constants.steakModificationPrice, vault.steakUpgrades))) {
                vault.steakUpgrades++
                vault.steakCollectDelay = calculateDelay(Constants.steakProductionTime, vault.steakUpgrades)
            }
        }
    }

    fun buyCola() {
        if (vault.hasBoughtCola) {
            alert("You have already bought water, maybe try upgrading")
        } else {
            if (hasEnoughMoney(Constants.colaInitialCost)) {
                vault.hasBoughtCola = true
            }
        }
    }

    fun upgradeCola() {
        if (!vault.hasBoughtCola) {
            alert("You have not bought this resource yet")
        } else {
            if (maxedOutUpgrades(vault.nukaColaUpgrades++)) {
                alert("This resource is maxed out")
                return
            }
            if (hasEnoughMoney(calculateResourceFee(Constants.colaModificationPrice, vault.nukaColaUpgrades))) {
                vault.nukaColaUpgrades++
                vault.colaCollectDelay = calculateDelay(Constants.colaProductionTime, vault.nukaColaUpgrades)
            }
        }
    }

    fun placeDisposableEvents() {
        disposableController.placeSaveEvents()
    }

    fun killDisposables() {
        disposableController.dispose()
    }

    fun collectWater() {
        TODO("Not yet implemented")
    }
}