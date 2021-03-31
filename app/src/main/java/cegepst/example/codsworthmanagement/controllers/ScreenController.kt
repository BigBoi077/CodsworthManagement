package cegepst.example.codsworthmanagement.controllers

import android.widget.ImageButton
import android.widget.ProgressBar
import cegepst.example.codsworthmanagement.R
import cegepst.example.codsworthmanagement.models.BitwiseManager
import cegepst.example.codsworthmanagement.models.Collectible
import cegepst.example.codsworthmanagement.models.Constants
import cegepst.example.codsworthmanagement.models.Vault
import cegepst.example.codsworthmanagement.views.MainActivity
import java.lang.ref.WeakReference
import kotlin.math.roundToInt

class ScreenController(weakReference: WeakReference<MainActivity>) {

    private val weakReference = weakReference
    private val waterBuy = getComponent(R.id.actionBuyWater)
    private val waterUpgrade = getComponent(R.id.actionUpgradeWater)
    private val waterMrHandy = getComponent(R.id.actionMrHandyWater)
    private val steakBuy = getComponent(R.id.actionBuySteak)
    private val steakUpgrade = getComponent(R.id.actionUpgradeSteak)
    private val steakMrHandy = getComponent(R.id.actionMrHandySteak)
    private val colaBuy = getComponent(R.id.actionBuyCola)
    private val colaUpgrade = getComponent(R.id.actionUpgradeCola)
    private val colaMrHandy = getComponent(R.id.actionMrHandyCola)
    private val progressBars = HashMap<String, ProgressBar>()

    init {
        progressBars["water"] = weakReference.get()!!.findViewById(R.id.waterProgress)
        progressBars["cola"] = weakReference.get()!!.findViewById(R.id.colaProgress)
        progressBars["steak"] = weakReference.get()!!.findViewById(R.id.steakProgress)
    }

    fun lockAccordingButtons(vault: Vault) {
        verifyButtons(waterBuy, waterUpgrade, vault.hasBoughtWater)
        verifyButtons(steakBuy, steakUpgrade, vault.hasBoughtSteak)
        verifyButtons(colaBuy, colaUpgrade, vault.hasBoughtCola)
        verifyButton(waterMrHandy, vault.mrHandy, Constants.waterBitwiseValue)
        verifyButton(steakMrHandy, vault.mrHandy, Constants.steakBitwiseValue)
        verifyButton(colaMrHandy, vault.mrHandy, Constants.colaBitwiseValue)
    }

    fun updateProgressBars(collectibles: HashMap<String, Collectible>) {
        for (bar in progressBars) {
            val interval = calculateInterval(collectibles[bar.key])
            bar.value.max = interval
            if (collectibles[bar.key]!!.canCollect) {
                bar.value.progress = bar.value.max
            } else {
                bar.value.progress = collectibles[bar.key]!!.progress
            }
        }
    }

    private fun calculateInterval(collectible: Collectible?): Int {
        return collectible!!.interval.roundToInt() * 100
    }

    private fun verifyButton(button: ImageButton?, mrHandy: Int, bitwiseValue: Int) {
        if (BitwiseManager.hasMrHandy(mrHandy, bitwiseValue)) {
            button?.isEnabled = false
            button?.isClickable = false
        }
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

    private fun getComponent(id: Int): ImageButton? {
        return weakReference.get()?.findViewById(id)
    }

    fun resetProgressbar(item: String) {
        progressBars[item]!!.progress = 0
    }
}