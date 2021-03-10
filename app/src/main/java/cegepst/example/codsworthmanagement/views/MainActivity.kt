package cegepst.example.codsworthmanagement.views

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cegepst.example.codsworthmanagement.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        promptWelcome()
    }

    private fun promptWelcome() {
        Toast.makeText(
            this,
            "Welcome to vault ${intent.getStringExtra("vaultNumber")}",
            Toast.LENGTH_SHORT
        ).show()
    }
}