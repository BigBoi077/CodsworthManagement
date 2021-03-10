package cegepst.example.codsworthmanagement.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cegepst.example.codsworthmanagement.R

class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
    }

    fun onClick(view: View) {
        val vaultNumber = findViewById<EditText>(R.id.editTextVaultNumber).text
        if (vaultNumber.equals("")) {
            Toast.makeText(this, R.string.alertEmptyField, Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("vaultNumber", vaultNumber)
        startActivity(intent)
    }
}