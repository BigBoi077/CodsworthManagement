package cegepst.example.codsworthmanagement.views

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cegepst.example.codsworthmanagement.R
import cegepst.example.codsworthmanagement.controllers.MainController
import cegepst.example.codsworthmanagement.models.Vault
import cegepst.example.codsworthmanagement.models.VaultManager
import cegepst.example.codsworthmanagement.stores.AppStore

class MainActivity : AppCompatActivity() {

    private lateinit var manager: VaultManager
    private lateinit var controller: MainController
    private var vaultNumber: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        promptWelcome()
        initContent()
    }

    override fun onPause() {
        super.onPause()
        controller.saveVault()
    }

    override fun onDestroy() {
        super.onDestroy()
        controller.saveVault()
        controller.kill()
    }

    private fun initContent() {
        var lambda = { vault: Vault -> loadVault(vault) }
        manager = VaultManager(AppStore(this), vaultNumber)
        manager.handleVaultLoad(lambda)
    }

    private fun promptWelcome() {
        val welcomeText = findViewById<TextView>(R.id.welcomePrompt)
        val vaultNumber = intent.getStringExtra("vaultNumber")
        this.vaultNumber = vaultNumber.toString().toLong()
        welcomeText.text = "Welcome to vault $vaultNumber"
    }

    fun onClick(view: View) {
        controller.updateButtons()
        when (view.id) {
            R.id.actionBuyWater -> controller.buyWater()
            R.id.actionUpgradeWater -> controller.upgradeWater()
            R.id.actionCollectWater -> controller.collectWater()
            R.id.actionMrHandyWater -> controller.buyWaterMrHandy()

            R.id.actionBuySteak -> controller.buySteak()
            R.id.actionUpgradeSteak -> controller.upgradeSteak()
            R.id.actionCollectSteak -> controller.collectSteak()
            R.id.actionMrHandySteak -> controller.buySteakMrHandy()

            R.id.actionBuyCola -> controller.buyCola()
            R.id.actionUpgradeCola -> controller.upgradeCola()
            R.id.actionCollectCola -> controller.collectCola()
            R.id.actionMrHandyCola -> controller.buyColaMrHandy()
        }
        controller.saveVault()
        controller.printVault()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.actionDestroyVault -> {
            val intent = Intent(this, LogInActivity::class.java)
            intent.putExtra("vaultId", this.vaultNumber)
            startActivity(intent)
            true
        }
        R.id.actionQuitVault -> {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun loadVault(vault: Vault) {
        this.controller = MainController(this, vault)
        this.controller.initVariables()
        this.controller.refreshContent()
        this.controller.updateButtons()
        this.controller.start()
    }
}