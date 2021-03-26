package cegepst.example.codsworthmanagement.controllers

import android.util.Log
import android.widget.TextView
import android.widget.Toast
import cegepst.example.codsworthmanagement.R
import cegepst.example.codsworthmanagement.models.Constants
import cegepst.example.codsworthmanagement.models.Vault
import cegepst.example.codsworthmanagement.stores.AppStore
import cegepst.example.codsworthmanagement.views.MainActivity
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference

private const val PRODUCTION_TIME_RATIO = 0.05

class MainController(mainActivity: MainActivity, vault: Vault) {

    private var vault = vault
    private var weakReference = WeakReference(mainActivity)
    private val database = AppStore(mainActivity)
    private val screenManager = ScreenController(weakReference)
    private val gameController = GameController(this, vault)

    private lateinit var capsView: TextView
    private lateinit var waterView: TextView
    private lateinit var steakView: TextView
    private lateinit var colaView: TextView

    fun kill() {
        gameController.dispose()
    }

    fun start() {
        gameController.start()
    }

    fun initVariables() {
        capsView = weakReference.get()!!.findViewById(R.id.quantityCaps)
        waterView = weakReference.get()!!.findViewById(R.id.quantityWater)
        steakView = weakReference.get()!!.findViewById(R.id.quantitySteak)
        colaView = weakReference.get()!!.findViewById(R.id.quantityCola)
    }

    fun refreshContent() {
        GlobalScope.launch {
            vault = database.vaultDAO().get(vault.id)
            withContext(Dispatchers.Main) {
                capsView.text = "${vault.nbrCaps} caps"
                waterView.text = "${vault.waterUpgrades} water"
                steakView.text = "${vault.steakUpgrades} steaks"
                colaView.text = "${vault.nukaColaUpgrades} cola"
            }
        }
    }

    fun saveVault() {
        GlobalScope.launch {
            database.vaultDAO().update(vault)
            refreshContent()
        }
    }

    fun updateButtons() {
        screenManager.lockAccordingButtons(vault)
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

    private fun calculateDelay(productionTime: Double, nbrUpgrades: Int): Double {
        return productionTime - (productionTime * (nbrUpgrades * PRODUCTION_TIME_RATIO))
    }

    fun buyWater() {
        if (vault.hasBoughtWater) {
            alert("You have already bought water, maybe try upgrading")
        } else {
            if (hasEnoughMoney(Constants.waterInitialCost)) {
                vault.hasBoughtWater = true
                vault.nbrCaps -= Constants.waterInitialCost
                saveVault()
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
            val cost = calculateResourceFee(Constants.waterModificationPrice, vault.waterUpgrades)
            if (hasEnoughMoney(cost)) {
                vault.waterUpgrades++
                vault.waterCollectDelay = calculateDelay(Constants.waterProductionTime, vault.waterUpgrades)
                vault.nbrCaps -= cost
                gameController.changeInterval("water", vault.waterCollectDelay)
            }
        }
        saveVault()
    }

    fun collectWater() {
        if (gameController.canCollect("water")) {
            alert("Collected water")
            vault.nbrCaps += Constants.waterRevenue
            gameController.hasCollected("water")
            gameController.setNewTimestamp("water")
        }
        saveVault()
    }

    fun buySteak() {
        if (vault.hasBoughtSteak) {
            alert("You have already bought water, maybe try upgrading")
        } else {
            if (hasEnoughMoney(Constants.steakInitialCost)) {
                vault.hasBoughtSteak = true
                vault.nbrCaps -= Constants.steakInitialCost
                saveVault()
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
            val cost = calculateResourceFee(Constants.steakModificationPrice, vault.steakUpgrades)
            if (hasEnoughMoney(cost)) {
                vault.steakUpgrades++
                vault.steakCollectDelay = calculateDelay(Constants.steakProductionTime, vault.steakUpgrades)
                vault.nbrCaps -= cost
            }
        }
        saveVault()
    }

    fun collectSteak() {
        if (gameController.canCollect("steak")) {
            alert("Collected steak")
            vault.nbrCaps += Constants.steakRevenue
            gameController.hasCollected("steak")
            gameController.setNewTimestamp("steak")
        }
        saveVault()
    }

    fun buyCola() {
        if (vault.hasBoughtCola) {
            alert("You have already bought water, maybe try upgrading")
        } else {
            if (hasEnoughMoney(Constants.colaInitialCost)) {
                vault.hasBoughtCola = true
                vault.nbrCaps -= Constants.colaInitialCost
                saveVault()
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
            val cost = calculateResourceFee(Constants.colaModificationPrice, vault.nukaColaUpgrades)
            if (hasEnoughMoney(cost)) {
                vault.nukaColaUpgrades++
                vault.colaCollectDelay = calculateDelay(Constants.colaProductionTime, vault.nukaColaUpgrades)
                vault.nbrCaps -= cost
            }
        }
        saveVault()
    }

    fun collectCola() {
        if (gameController.canCollect("cola")) {
            alert("Collected cola")
            vault.nbrCaps += Constants.colaRevenue
            gameController.hasCollected("cola")
            gameController.setNewTimestamp("cola")
        }
        saveVault()
    }

    fun printVault() {
        Log.d("VAULT", Gson().toJson(vault))
    }
}