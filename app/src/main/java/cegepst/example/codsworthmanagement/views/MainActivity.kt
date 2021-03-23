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
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

private const val SAVE_INTERVAL = 60

class MainActivity : AppCompatActivity() {

    private lateinit var disposable: Disposable
    private lateinit var manager: VaultManager
    private lateinit var controller: MainController
    private lateinit var running: AtomicBoolean
    private lateinit var vault: Vault
    private var vaultNumber: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        promptWelcome()
        initContent()
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
        welcomeText.text = "Welcome to vault ${vaultNumber}"
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.actionBuyWater -> controller.buyWater()
        }
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
        this.vault = vault
        this.controller = MainController(this, vault)
        this.controller.initVariables()
        this.controller.loadContent()
        this.running.set(true)
        this.controller.updateButtons()
        placeUpdateEvent()
    }

    private fun placeUpdateEvent() {
        disposable = Observable.interval(SAVE_INTERVAL.toLong(), TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .filter { running.get() }
                .map { controller.updateContent() }
                .subscribe { controller.loadContent() }
    }
}