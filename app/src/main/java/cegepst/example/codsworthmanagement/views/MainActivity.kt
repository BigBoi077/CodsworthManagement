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
        manager.handleVaultLoad()
    }

    private fun promptWelcome() {
        val welcomeText = findViewById<TextView>(R.id.welcomePrompt)
        val vaultNumber = intent.getStringExtra("vaultNumber")
        this.vaultNumber = vaultNumber.toString().toLong()
        welcomeText.text = "Welcome to vault ${vaultNumber}"
    }

    fun onClick(view: View) {
        // TODO : fill actions here
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

    fun loadVault(vault: Vault) {
        this.vault = vault
        this.controller = MainController(this, vault)
    }
}