package cegepst.example.codsworthmanagement.controllers

import android.widget.Button
import cegepst.example.codsworthmanagement.R
import cegepst.example.codsworthmanagement.models.Vault
import cegepst.example.codsworthmanagement.views.MainActivity
import java.lang.ref.WeakReference

class ScreenController(weakReference: WeakReference<MainActivity>, vault: Vault) {

    private var vault = vault
    private val waterBuy = weakReference.get()?.findViewById<Button>(R.id.actionBuyWater)
    private val waterUpgrade = weakReference.get()?.findViewById<Button>(R.id.actionUpgradeWater)
    private val steakBuy = weakReference.get()?.findViewById<Button>(R.id.actionBuySteak)
    private val steakUpgrade = weakReference.get()?.findViewById<Button>(R.id.actionUpgradeSteak)
    private val colaBuy = weakReference.get()?.findViewById<Button>(R.id.actionBuyCola)
    private val colaUpgrade = weakReference.get()?.findViewById<Button>(R.id.actionUpgradeCola)

    fun lockAccordingButtons() {
        verifyButtons(waterBuy, waterUpgrade, vault.hasBoughtWater)
        verifyButtons(steakBuy, steakUpgrade, vault.hasBoughtSteak)
        verifyButtons(colaBuy, colaUpgrade, vault.hasBoughtCola)
    }

    private fun verifyButtons(buy: Button?, upgrade: Button?, hasBought: Boolean) {
        if (hasBought) {
            toggleUpgrade(buy, upgrade)
        } else {
            toggleBuy(buy, upgrade)
        }
    }

    private fun toggleUpgrade(buy: Button?, upgrade: Button?) {
        buy?.isEnabled = false
        buy?.isClickable = false
        upgrade?.isEnabled = true
        upgrade?.isClickable = true
    }

    private fun toggleBuy(buy: Button?, upgrade: Button?) {
        upgrade?.isEnabled = false
        upgrade?.isClickable = false
        buy?.isEnabled = true
        buy?.isClickable = true
    }
}