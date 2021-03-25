package cegepst.example.codsworthmanagement.controllers

import android.widget.ImageButton
import cegepst.example.codsworthmanagement.R
import cegepst.example.codsworthmanagement.models.Vault
import cegepst.example.codsworthmanagement.views.MainActivity
import java.lang.ref.WeakReference

class ScreenController(weakReference: WeakReference<MainActivity>) {

    private val waterBuy = weakReference.get()?.findViewById<ImageButton>(R.id.actionBuyWater)
    private val waterUpgrade =
        weakReference.get()?.findViewById<ImageButton>(R.id.actionUpgradeWater)
    private val steakBuy = weakReference.get()?.findViewById<ImageButton>(R.id.actionBuySteak)
    private val steakUpgrade =
        weakReference.get()?.findViewById<ImageButton>(R.id.actionUpgradeSteak)
    private val colaBuy = weakReference.get()?.findViewById<ImageButton>(R.id.actionBuyCola)
    private val colaUpgrade = weakReference.get()?.findViewById<ImageButton>(R.id.actionUpgradeCola)

    fun lockAccordingButtons(vault: Vault) {
        verifyButtons(waterBuy, waterUpgrade, vault.hasBoughtWater)
        verifyButtons(steakBuy, steakUpgrade, vault.hasBoughtSteak)
        verifyButtons(colaBuy, colaUpgrade, vault.hasBoughtCola)
    }

    private fun verifyButtons(buy: ImageButton?, upgrade: ImageButton?, hasBought: Boolean) {
        if (hasBought) {
            toggleUpgrade(buy, upgrade)
        } else {
            toggleBuy(buy, upgrade)
        }
    }

    private fun toggleUpgrade(buy: ImageButton?, upgrade: ImageButton?) {
        buy?.isEnabled = false
        buy?.isClickable = false
        upgrade?.isEnabled = true
        upgrade?.isClickable = true
    }

    private fun toggleBuy(buy: ImageButton?, upgrade: ImageButton?) {
        upgrade?.isEnabled = false
        upgrade?.isClickable = false
        buy?.isEnabled = true
        buy?.isClickable = true
    }
}