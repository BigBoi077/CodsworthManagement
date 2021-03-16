package cegepst.example.codsworthmanagement.views

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cegepst.example.codsworthmanagement.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        promptWelcome()
    }

    private fun promptWelcome() {
        val welcomeText = findViewById<TextView>(R.id.welcomePrompt)
        welcomeText.text = "Welcome to vault ${intent.getStringExtra("vaultNumber")}"
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