package cegepst.example.codsworthmanagement.views

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

class MainActivity : AppCompatActivity() {

    private lateinit var manager: VaultManager
    private lateinit var controller: MainController
    private lateinit var vault: Vault
    private var vaultNumber: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        promptWelcome()
        initContent()
    }

    private fun initContent() {
        manager = VaultManager(this, vaultNumber)
        if (manager.vaultExit()) {
            manager.loadVaultContent()
        } else {
            manager.registerVault()
        }
        this.controller = MainController(this)
    }

    private fun promptWelcome() {
        val welcomeText = findViewById<TextView>(R.id.welcomePrompt)
        val vaultNumber = intent.getStringExtra("vaultNumber")
        this.vaultNumber = vaultNumber.toString().toLong()
        welcomeText.text = "Welcome to vault ${vaultNumber}"
    }

    fun onClick(view: View) {}

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.actionDestroyVault -> {
            // TODO : make method here
            true
        }
        R.id.actionQuitVault -> {
            // TODO : make method here
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}